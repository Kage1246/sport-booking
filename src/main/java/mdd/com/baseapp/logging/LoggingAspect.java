package mdd.com.baseapp.logging;

import static mdd.com.baseapp.common.util.StringUtil.shortenString;

import java.util.Arrays;
import mdd.com.baseapp.exception.CustomException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect for logging execution of service and repository Spring components.
 * <p>
 * By default, it only runs with the "dev" profile.
 */
@Aspect
public class LoggingAspect {

  public LoggingAspect() {
  }

  /**
   * Pointcut that matches all repositories, services and Web REST endpoints.
   */
  @Pointcut("within(@org.springframework.stereotype.Repository *)" +
      " || within(@org.springframework.stereotype.Service *)" +
      " || within(@org.springframework.web.bind.annotation.RestController *)")
  public void springBeanPointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  /**
   * Pointcut that matches all Spring beans in the application's main packages.
   */
  @Pointcut("within(mdd.com.baseapp.repository..*)" + " || within(mdd.com.baseapp.service..*)" +
      " || within(mdd.com.baseapp.controller..*)")
  public void applicationPackagePointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  /**
   * Retrieves the {@link Logger} associated to the given {@link JoinPoint}.
   *
   * @param joinPoint join point we want the logger for.
   * @return {@link Logger} associated to the given {@link JoinPoint}.
   */
  private Logger logger(JoinPoint joinPoint) {
    return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
  }

  /**
   * Advice that logs methods throwing exceptions.
   *
   * @param joinPoint join point for advice.
   * @param e         exception.
   */
  @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    {
      if (e instanceof CustomException) {
        logger(joinPoint).info("Exception in {}() and exception = '{}'",
            joinPoint.getSignature().getName(), e.getMessage());
      } else {
        logger(joinPoint).error("Exception in {}() with cause = '{}' and exception = '{}'",
            joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL",
            e.getMessage(), e);
      }
    }
  }

  /**
   * Advice that logs when a method is entered and exited.
   *
   * @param joinPoint join point for advice.
   * @return result.
   * @throws Throwable throws {@link IllegalArgumentException}.
   */
  @Around("applicationPackagePointcut() && springBeanPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    Logger log = logger(joinPoint);

    String methodName = joinPoint.getSignature().getName();
    String argsString = shortenString(Arrays.toString(joinPoint.getArgs()), 30);
    log.info("Enter: {}() with argument[s] = {}", methodName, argsString);

    try {
      Object result = joinPoint.proceed();

      String resultString = shortenString(String.valueOf(result), 30);
      log.info("Exit: {}() with result = {}", methodName, resultString);
      return result;
    } catch (IllegalArgumentException e) {
      log.error("Error in {}() Illegal argument: {}", methodName,
          Arrays.toString(joinPoint.getArgs()));
      throw e;
    }
  }
}

package mdd.com.baseapp.config;


import mdd.com.baseapp.logging.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

  @Bean
  public LoggingAspect loggingAspect() {
    return new LoggingAspect();
  }
}
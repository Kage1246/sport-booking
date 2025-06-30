package mdd.com.baseapp.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@Log4j2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException e) throws IOException {
    log.error("Responding with unauthorized error. Message - {}, URL - {}", e.getMessage(),
        request.getRequestURL());
//        log.error("Responding with unauthorized error. Message - {}", e.getMessage());
//    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    resolver.resolveException(request, response, null, e);
  }
}
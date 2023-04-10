package org.daemio.merch.filter;

import java.util.Arrays;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class JWTAuthorizationFilter implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("Handle : {}", handler.getClass());

    if (handler instanceof HandlerMethod) {
      var handlerMethod = (HandlerMethod) handler;

      Arrays.stream(handlerMethod.getMethodParameters()).forEach(p -> log.info("Handle : {}", p));
      log.info("Handle : {}", handlerMethod.getBean());
    }

    return true;
  }
}

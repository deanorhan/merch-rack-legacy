package org.daemio.merch.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class CustomMethodHandler implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("Handle : {}", handler.getClass());

    if (handler instanceof HandlerMethod) {

      log.info("{}: {}", request.getRequestURI(), request);
    }

    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable ModelAndView modelAndView)
      throws Exception {
    log.info("Post Handle : {}", handler.getClass());

    if (handler instanceof HandlerMethod) {
      log.info("{}: {}", request.getRequestURI(), response);
    }
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable Exception ex)
      throws Exception {
    log.info("Completion : {}", handler.getClass());

    if (handler instanceof HandlerMethod) {

      log.info("{}: {}", request.getRequestURI(), response);
    }
  }
}

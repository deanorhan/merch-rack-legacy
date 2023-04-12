package org.daemio.merch.filter;

import javax.security.auth.login.LoginException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import org.daemio.merch.annotations.LoginRequired;
import org.daemio.merch.config.UserContext;

@Component
@AllArgsConstructor
public class LoggedInMethodHandler implements HandlerInterceptor {

  private UserContext userContext;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (handler instanceof HandlerMethod) {
      var handlerMethod = (HandlerMethod) handler;
      var loginAnnotation = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
      if (userContext.isPublicUser() && loginAnnotation != null) {
        throw new LoginException("need to be logged in for this");
      }
    }

    return true;
  }
}

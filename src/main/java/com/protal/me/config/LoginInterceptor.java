package com.protal.me.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);
    public static final String SESSION_USER = "SESSION_USER";

    //登录拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute(SESSION_USER);
        LOG.info("LoginInterceptor : {} -> {}", user, request.getRequestURI());
        if (null == user) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return false;
        }
        return true;
    }

}
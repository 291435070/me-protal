package com.protal.me.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class DefaultView implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //设置默认首页
        registry.addViewController("/index").setViewName("forward:/login.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //未登录不拦截的请求
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns("/login.html", "/index/login", "/js/**", "/css/**");
    }
}
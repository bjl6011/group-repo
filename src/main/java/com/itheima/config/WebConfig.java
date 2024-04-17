package com.itheima.config;

import com.itheima.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;


    public void addInterceptors(InterceptorRegistry registry) {
        //登录和注册接口不拦截
        // adadad
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login","/user/register","/user/token","/advertisingspace");
    }
}
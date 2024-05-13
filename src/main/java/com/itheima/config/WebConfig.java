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
        registry.addInterceptor(loginInterceptor).excludePathPatterns(
                "/user/login",
                "/user/register",
                "/user/token",
                "/advertisingspace",
                "/manager/managerLogin",
                "/manager/initialize",
                "/manager/details",
                "/manager/search",
                "/manager/add",
                "/user/msg",
                "/manager/updateNews",
                "/manager/deleteNews",
                "/manager/publishNews",
                "/manager/getUnauditedFeeds",
                "/manager/updateFeedAudit",
                "/manager/getFeedsByAuditStatus",
                "/manager/getFeedById",
                "/manager/deleteFeedById",
                "/manager/getUnauditFeeds",
                "/manager/getAuditFeeds"
        );
    }
}

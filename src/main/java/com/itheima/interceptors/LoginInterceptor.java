package com.itheima.interceptors;

import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        if(token==""){
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",0);
            claims.put("username","未知用户");
            ThreadLocalUtil.set(claims);
            return true;
        }

        try{
            //redis获取token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

            String redisToken=operations.get(token);
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //业务数据存在ThreadLocal中
            ThreadLocalUtil.set(claims);
            return true;
        }catch(Exception e){
            //不放行
            response.setStatus(401);
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
         ThreadLocalUtil.remove();
    }
}

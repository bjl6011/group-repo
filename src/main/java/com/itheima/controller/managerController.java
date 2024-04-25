package com.itheima.controller;

import com.itheima.DTO.loginDTO;
import com.itheima.pojo.Manager;
import com.itheima.pojo.Result;
import com.itheima.service.ManagerService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/manager")
public class managerController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 管理员登录
    @PostMapping("/managerLogin")
    public Result managerLogin(@RequestBody loginDTO loginDTO) {

        Manager manager = managerService.fingByManagerName(loginDTO.getUsername());
        if(manager == null) {
            return Result.error2("用户名错误");
        }
        else {
            if(loginDTO.getPassword().equals(manager.getPassword())) {

                return Result.success("登陆成功");
            }
            else {
                return Result.error2("密码错误");
            }
        }
    }
}

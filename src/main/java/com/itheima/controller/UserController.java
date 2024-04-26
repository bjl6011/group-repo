package com.itheima.controller;

import com.itheima.DTO.loginDTO;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 注册
    @PostMapping("/register")
    public Result register(@RequestBody loginDTO loginDTO){

            //查询用户被占用
            User user = userService.findByUserName(loginDTO.getUsername());
            if (user == null) {
                userService.register(loginDTO.getUsername(), loginDTO.getPassword());
                return Result.success();
            } else {
                return Result.error2("用户名已被占用");
            }
    }

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody loginDTO loginDTO){

        //查询用户
        User user = userService.findByUserName(loginDTO.getUsername());
        //是否存在

        if(user==null)
            return Result.error2("用户名错误");

        //判断密码
        else{
            if(Md5Util.getMD5String(loginDTO.getPassword()).equals(user.getPassword())) {
                Map<String,Object> claims=new HashMap<>();
                claims.put("id",user.getId());
                claims.put("username",user.getUsername());
                String token= JwtUtil.genToken(claims);
                //token存在redis
                ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
                operations.set(token, token, 24, TimeUnit.HOURS);
                return Result.success(token);
            }
            else
                return Result.error2("密码错误");
        }
    }

    @GetMapping("/info")
    public Result<User> userinfo(){

        Map<String,Object> map = ThreadLocalUtil.get();
        String username=(String)map.get("username");
        User user=userService.findByUserName(username);
        return Result.success(user);
    }

    @GetMapping("/token")
    public Result DefaultToken(){
        Map<String,Object> claims=new HashMap<>();
        claims.put("id",0);
        claims.put("username","未知用户");
        String token= JwtUtil.genToken(claims);
        return Result.success(token);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd))
            return  Result.error("缺少必要参数");

        //原密码是否正确
        Map<String,Object> map=ThreadLocalUtil.get();
        String username=(String)map.get("username");
        User loginUser = userService.findByUserName(username);
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }

        if(!newPwd.equals(rePwd)){
            return Result.error("两次填写密码不一样");
        }

        userService.updatePwd(newPwd);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}

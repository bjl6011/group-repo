package com.itheima.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class userVO {
    private Integer id;//主键ID

    private String username;//用户名

    private String nickname;//昵称

    private String email;//邮箱

    private String userPic;//用户头像地址

    private Integer likeCount;
    private Integer commentCount;
}

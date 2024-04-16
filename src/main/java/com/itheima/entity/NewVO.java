package com.itheima.entity;

import com.itheima.pojo.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewVO {
    @NotNull
    int id;
    @NotNull
    private String title;
    private String subject;
    private String content;
    private User user;
    int hit;//点击量
    Integer likeCount;
    private Integer commentCount;
    private String cover;
    private String createTime;
}

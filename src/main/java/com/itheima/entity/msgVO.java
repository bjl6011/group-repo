package com.itheima.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class msgVO {

    private String nickname; // 评论人昵称
    private String userPic; // 评论人头像

    private LocalDateTime time;

    private String content;

    private String cover; // 动态封面



}

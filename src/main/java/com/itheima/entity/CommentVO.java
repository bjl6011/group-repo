package com.itheima.entity;

import com.itheima.pojo.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVO {

    Integer id;
    User user;

    String body;

    LocalDateTime createTime;

}

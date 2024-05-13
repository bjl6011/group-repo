package com.itheima.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class New {
    @NotNull
    int id; //
    @NotNull
    String title; //
    String content; //
    String subject; //
    Category category;
    int userid;
    int hit;//点击量
    String image; //
    Integer likeCount;
    Integer commentCount;
    LocalDateTime createTime;
}

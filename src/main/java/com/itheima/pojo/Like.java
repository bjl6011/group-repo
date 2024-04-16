package com.itheima.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Like {
    int id;
    int userID;//点赞者
    int targetID;//发布者

    private Integer likeableId;//被点赞的动态/资讯id
    private String likeableType;
    private LocalDateTime createTime;
}

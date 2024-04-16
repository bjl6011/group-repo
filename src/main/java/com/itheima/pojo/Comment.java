package com.itheima.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    @NotNull
    private Integer id;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer targetId;
    private String body;
    private Integer commentableId;//被评论的动态/资讯id
    private String commentableType;
    private LocalDateTime createTime;
}

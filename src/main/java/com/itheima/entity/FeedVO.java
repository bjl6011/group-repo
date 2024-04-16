package com.itheima.entity;

import com.itheima.pojo.Like;
import com.itheima.pojo.Topic;
import com.itheima.pojo.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class FeedVO {
    private Integer id;//主键ID
    private User user;
    private String feedContent;

    private Integer likeCount;
    private Integer feedViewCount;

    private Integer commentCount;
    private String cover;

    private List<String> images;
    private boolean hasLike;
    private Topic topics;
    private String createTime;
}

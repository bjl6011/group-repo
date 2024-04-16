package com.itheima.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Feed {
    @NotNull
    private Integer id;//主键ID

    @NotNull
    private Integer userID;

    @NotNull
    private String feedContent;

    private Integer likeCount;
    private Integer commentCount;
    private Integer feedViewCount;
    private Integer auditStatus;
    private String images;
    private Topic topics;
    private LocalDateTime createTime;

}

package com.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Manager {
    @NotNull
    private Integer id;
    private String username;
    @JsonIgnore//让springmvc把当前对象转化成json时忽略password
    private String password;

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname; // 昵称

    private String userPic; // 管理员头像地址
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

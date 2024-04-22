package com.itheima.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class manager {
    @NotNull
    private Integer id;
    private String username;
    private String password;

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname; // 昵称
    private String userPic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

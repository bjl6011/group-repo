package com.itheima.administer.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTimeVO {
    @NotNull
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

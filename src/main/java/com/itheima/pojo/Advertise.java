package com.itheima.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Advertise {
    @NotNull
    private Integer id;//主键ID
    @NotEmpty
    @URL
    private String image;//封面图像
    @NotEmpty
    @URL
    private String link;//跳转广告

}

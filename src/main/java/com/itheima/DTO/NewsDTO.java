package com.itheima.DTO;

import lombok.Data;

@Data
public class NewsDTO {
    int id; // 资讯id
    String title; // 标题
    String subject; // 主题
    String content; // 内容
    String image; // 图像
}

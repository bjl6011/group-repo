package com.itheima.service;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;

public interface ArticleService {
    Article findById(Integer id) ;

    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);

    void update(Article article);

    void delete(Integer id);
}

package com.itheima.controller;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;


    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum,Integer pageSize,
                                        @RequestParam(required = false)String categoryId,
                                        @RequestParam(required = false)String state){
        PageBean<Article> pb=articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article){
        articleService.update(article);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<Article> detail(Integer id){
        Article article=articleService.findById(id);
        return Result.success(article);
    }

    @DeleteMapping
    public Result delete(Integer id){
        articleService.delete(id);
        return Result.success();
    }

}

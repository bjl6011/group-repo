package com.itheima.controller;


import com.itheima.pojo.Comment;
import com.itheima.pojo.Result;
import com.itheima.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itheima.entity.CommentVO;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/feeds")
    public Result<List<CommentVO>> getFeedComments(Integer id){
        List<CommentVO> comments=commentService.getFeedComments(id);

        return Result.success(comments);
    }

    @GetMapping("/news")
    public Result<List<CommentVO>> getNewComments(Integer id){
        List<CommentVO> comments=commentService.getNewComments(id);

        return Result.success(comments);
    }
}

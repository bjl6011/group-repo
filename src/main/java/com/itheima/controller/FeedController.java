package com.itheima.controller;

import com.itheima.DTO.FeedDTO;
import com.itheima.DTO.commentDTO;
import com.itheima.entity.FeedVO;
import com.itheima.pojo.Article;
import com.itheima.pojo.Feed;
import com.itheima.pojo.Result;
import com.itheima.service.FeedService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/feeds")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping
    public Result<List<FeedVO>> list(){
        List<FeedVO> feedsvo=feedService.list();
        return Result.success(feedsvo);
    }

    @GetMapping("/info")
    public Result<FeedVO> getOne(Integer id){
        FeedVO feedVO=feedService.getById(id);

        return Result.success(feedVO);
    }

    @GetMapping("/user")
    public Result<List<FeedVO>> listByUser( ){

        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        List<FeedVO> feedsvo=feedService.listByUser(userId);
        return Result.success(feedsvo);
    }

    @PostMapping("/like")
    public Result likeFeed(Integer id){
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        feedService.likeFeed(userId,id);
        return Result.success();
    }

    @PostMapping("unlike")
    public Result unLikeFeed(Integer id){
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        feedService.unLikeFeed(userId,id);
        return Result.success();
    }

    @PostMapping("/view")
    public Result addViewCount(Integer id){
        feedService.addViewCount(id);

        return Result.success();
    }
    @PostMapping("/comments")
    public Result addComment(@RequestBody commentDTO commentDTO){

        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");

        feedService.addComment(userId,commentDTO.getId(),commentDTO.getBody());

        return Result.success();
    }

    @PostMapping("/send")
    public Result sendFeed(@RequestBody FeedDTO feedDTO){
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");


        feedService.addFeed(userId,feedDTO.getContent(),feedDTO.getImages());

        return Result.success();
    }
}

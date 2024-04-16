package com.itheima.controller;

import com.itheima.DTO.commentDTO;
import com.itheima.entity.NewVO;
import com.itheima.pojo.New;
import com.itheima.pojo.Result;
import com.itheima.service.NewService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/news")
public class NewController {

    @Autowired
    private NewService newService;

    @GetMapping
    public Result<List<NewVO>> list(){
        List<NewVO> news=newService.list();

        return Result.success(news);
    }

    @GetMapping("/info")
    public Result<NewVO> getOne(Integer id){
        NewVO newVO=newService.findById(id);

        return Result.success(newVO);
    }

    @PostMapping("/like")
    public Result likeNew(Integer id){
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");

        newService.likeNew(userId,id);
        return Result.success();
    }

    @PostMapping("/unlike")
    public Result unLikeNew(Integer id){
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");

        newService.unlikeNew(userId,id);
        return Result.success();
    }

    @PostMapping("/comments")
    public Result addComment(@RequestBody commentDTO commentDTO){
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");

        newService.addComment(userId,commentDTO.getId(),commentDTO.getBody());

        return Result.success();
    }


}

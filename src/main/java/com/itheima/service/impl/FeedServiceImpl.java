package com.itheima.service.impl;

import com.itheima.assembler.FeedAssembler;
import com.itheima.entity.FeedVO;
import com.itheima.mapper.FeedMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Feed;
import com.itheima.pojo.User;
import com.itheima.service.FeedService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedMapper feedMapper;

    @Autowired
    private UserMapper userMapper;

    private FeedAssembler feedAssembler=new FeedAssembler();

    @Override
    public List<FeedVO> list() {

        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");


        List<Feed> feeds=feedMapper.list();
        List<FeedVO> feedVOS=new ArrayList<>();
        for(int i=0;i<feeds.size();i++){
            User user=userMapper.findByUserId(feeds.get(i).getUserID());
            boolean hasLike= feedMapper.hasLike(userId,feeds.get(i).getId())==null ? false:true;
            feedVOS.add(feedAssembler.assembler(feeds.get(i),user,hasLike));
        }
        return feedVOS;
    }

    @Override
    public FeedVO getById(Integer id) {

        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");

        FeedVO feedVO=new FeedVO();

        Feed feed=feedMapper.getById(id);
        User user=userMapper.findByUserId(feed.getUserID());
        boolean hasLike= feedMapper.hasLike(userId,feed.getId())==null ? false:true;
        feedVO=feedAssembler.assembler(feed,user,hasLike);


        return feedVO;
    }

    @Override
    public List<FeedVO> listByUser(Integer userid) {
        List<Feed> feeds=feedMapper.listByUser(userid);
        List<FeedVO> feedVOS=new ArrayList<>();
        for(int i=0;i<feeds.size();i++){
            User user=userMapper.findByUserId(feeds.get(i).getUserID());
            boolean hasLike= feedMapper.hasLike(userid,feeds.get(i).getId())==null ? false:true;
            feedVOS.add(feedAssembler.assembler(feeds.get(i),user,hasLike));
        }
        return feedVOS;
    }

    @Override
    public void likeFeed(Integer userId, Integer id) {
        int targetId=feedMapper.findUserById(id);
        feedMapper.like(userId,targetId,id);
        feedMapper.addlike(id);
    }

    @Override
    public void unLikeFeed(Integer userId, Integer id) {
        int targetId=feedMapper.findUserById(id);
        feedMapper.unlike(userId,targetId,id);
        feedMapper.offlike(id);
    }

    @Override
    public void addComment(Integer userId, Integer id, String body) {
        int targetId=feedMapper.findUserById(id);
        feedMapper.comment(userId,targetId,id,body);
        feedMapper.addComment(id);
    }

    @Override
    public void addFeed(Integer userId, String content, String images) {
        images="1"+images+"2";
        feedMapper.addFeed(userId,content,images);
    }

    @Override
    public void addViewCount(Integer id) {
        feedMapper.addViewCount(id);
    }


}

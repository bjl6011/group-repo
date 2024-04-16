package com.itheima.service;

import com.itheima.entity.FeedVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedService {
    List<FeedVO> list();

    FeedVO getById(Integer id);

    List<FeedVO> listByUser(Integer userid);

    void likeFeed(Integer userId, Integer id);

    void unLikeFeed(Integer userId, Integer id);

    void addComment(Integer userId, Integer id, String body);


    void addFeed(Integer userId, String content, String images);

    void addViewCount(Integer id);
}

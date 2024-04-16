package com.itheima.assembler;

import com.itheima.entity.FeedVO;
import com.itheima.pojo.Feed;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FeedAssembler {

    public FeedVO assembler(Feed feed, User user,boolean hasLike){
        FeedVO feedVO=new FeedVO();

        List<String> images = Arrays.asList(feed.getImages().split(","));
        String cover;
        if(images.size()==1)
            cover=images.get(0).substring(1, images.get(0).length()-1);
        else
            cover=images.get(0).substring(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createTime=feed.getCreateTime().format(formatter);

        feedVO.setId(feed.getId());
        feedVO.setFeedContent(feed.getFeedContent());
        feedVO.setImages(images);

        feedVO.setTopics(feed.getTopics());
        feedVO.setLikeCount(feed.getLikeCount());
        feedVO.setCommentCount(feed.getCommentCount());
        feedVO.setFeedViewCount(feed.getFeedViewCount());
        feedVO.setHasLike(hasLike);
        feedVO.setCover(cover);
        feedVO.setUser(user);
        feedVO.setCreateTime(createTime);

        return feedVO;
    }
}

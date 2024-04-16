package com.itheima.assembler;

import com.itheima.entity.NewVO;
import com.itheima.pojo.New;
import com.itheima.pojo.User;

import java.time.format.DateTimeFormatter;

public class NewAssembler {
    public NewVO assembler(User user, New news){
        NewVO newVO=new NewVO();


        String cover=news.getImage().substring(1, news.getImage().length()-1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createTime = news.getCreateTime().format(formatter);

        newVO.setUser(user);
        newVO.setId(news.getId());
        newVO.setContent(news.getContent());
        newVO.setSubject(news.getSubject());
        newVO.setHit(news.getHit());
        newVO.setCommentCount(news.getCommentCount());
        newVO.setLikeCount(news.getLikeCount());
        newVO.setCover(cover);
        newVO.setTitle(news.getTitle());
        newVO.setCreateTime(createTime);

        return newVO;
    }
}

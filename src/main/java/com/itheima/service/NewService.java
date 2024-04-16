package com.itheima.service;

import com.itheima.entity.NewVO;

import java.util.List;

public interface NewService {
    List<NewVO> list();

    NewVO findById(Integer id);

    void likeNew(Integer userid,Integer id);

    void unlikeNew(Integer userId, Integer id);

    void addComment(Integer userId, Integer id, String body);
}

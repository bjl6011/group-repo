package com.itheima.service;

import com.itheima.entity.CommentVO;
import com.itheima.pojo.Comment;

import java.util.List;

public interface CommentService {

    List<CommentVO> getFeedComments(Integer id);

    List<Comment> getFeedCommentByTargetId(Integer id);

    List<CommentVO> getNewComments(Integer id);
}

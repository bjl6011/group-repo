package com.itheima.service.impl;

import com.itheima.assembler.CommentAssembler;
import com.itheima.entity.CommentVO;
import com.itheima.mapper.CommentMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Comment;
import com.itheima.pojo.User;
import com.itheima.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    private CommentAssembler commentAssembler=new CommentAssembler();
    @Override
    public List<CommentVO> getFeedComments(Integer id) {
        List<Comment> comments=commentMapper.getFeedComments(id);

        List<CommentVO> commentVos=new ArrayList<>();

        for(int i=0;i<comments.size();i++){
            User user=userMapper.findByUserId(comments.get(i).getUserId());
            commentVos.add(commentAssembler.assembler(comments.get(i), user));
        }

        return commentVos;
    }

    @Override
    public List<CommentVO> getNewComments(Integer id) {
        List<Comment> comments=commentMapper.getNewComments(id);

        List<CommentVO> commentVos=new ArrayList<>();

        for(int i=0;i<comments.size();i++){
            User user=userMapper.findByUserId(comments.get(i).getUserId());
            commentVos.add(commentAssembler.assembler(comments.get(i), user));
        }

        return commentVos;
    }
}

package com.itheima.assembler;

import com.itheima.entity.CommentVO;
import com.itheima.pojo.Comment;
import com.itheima.pojo.User;

public class CommentAssembler {

    public CommentVO assembler(Comment comment, User user){
        CommentVO commentVO=new CommentVO();

        commentVO.setId(comment.getId());
        commentVO.setUser(user);
        commentVO.setBody(comment.getBody());
        commentVO.setCreateTime(comment.getCreateTime());

        return commentVO;
    }
}

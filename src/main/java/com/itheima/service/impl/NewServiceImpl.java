package com.itheima.service.impl;

import com.itheima.assembler.NewAssembler;
import com.itheima.entity.NewVO;
import com.itheima.mapper.NewMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.New;
import com.itheima.pojo.User;
import com.itheima.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewServiceImpl implements NewService {

    @Autowired
    private NewMapper newMapper;

    @Autowired
    private UserMapper userMapper;

    private NewAssembler newAssembler=new NewAssembler();
    @Override
    public List<NewVO> list() {
        List<New> news=newMapper.list();

        List<NewVO> newVOS=new ArrayList<>();
        for(int i=0;i<news.size();i++){
            User user=userMapper.findByUserId(news.get(i).getUserid());
            newVOS.add(newAssembler.assembler(user,news.get(i)));
        }

        return newVOS;
    }

    @Override
    public NewVO findById(Integer id) {

        New n=newMapper.findById(id);
        User user=userMapper.findByUserId(n.getUserid());

        NewVO newVO=newAssembler.assembler(user, n);


        return newVO;
    }

    @Override
    public void likeNew(Integer userid, Integer id) {
        int target_id=newMapper.findUserById(id);
        newMapper.like(userid,target_id,id);
        newMapper.addlike(id);
    }

    @Override
    public void unlikeNew(Integer userId, Integer id) {
        int targetId=newMapper.findUserById(id);
        newMapper.unlike(userId,targetId,id);
        newMapper.offlike(id);
    }

    @Override
    public void addComment(Integer userId, Integer id, String body) {
        int targetId=newMapper.findUserById(id);
        newMapper.comment(userId,targetId,id,body);
        newMapper.addComment(id);
    }


}

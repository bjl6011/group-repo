package com.itheima.service.impl;

import com.itheima.mapper.ManagerMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Manager;
import com.itheima.pojo.User;
import com.itheima.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Manager fingByManagerName(String managerName) {
        Manager manager = managerMapper.findByUserName(managerName);
        return manager;
    }

    @Autowired
    public void UserService(ManagerMapper managerMapper) {
        this.managerMapper = managerMapper;
    }

    public List<User> initialize(Integer pageNumber, Integer pageSize){
        return managerMapper.initialize(pageNumber,pageSize);
    }

    public Integer totalNum(){
        return managerMapper.totalNum();
    }

    public List<User> searchByOption(String optionSelect,String optionValue,Integer pageNumber, Integer pageSize){
        return managerMapper.searchByOption(optionSelect,optionValue,pageNumber,pageSize);
    }

    public List<User> details(Integer id){
        return managerMapper.detailUserMsg(id);
    }

    public Integer selectNum(String optionSelect,String optionValue){
        return managerMapper.selectNum(optionSelect,optionValue);
    }

    public void addUser(String username, String gender, String password, String nickname, String email){
        managerMapper.addUser(username,gender,password,nickname,email);
    }

    public void importUser(String username, String gender, String password, String nickname, String email){
        managerMapper.importUser(username,gender,password,nickname,email);
    }

    public void updateUser(Integer id, String username, String sex, String password, String nickname, String email){
        managerMapper.updateUser(id,username,sex,password,nickname,email);
    }

    public void deleteUser(Integer id){
        managerMapper.deleteUser(id);
    }

}

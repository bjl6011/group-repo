package com.itheima.service;

import com.itheima.entity.msgVO;
import com.itheima.pojo.User;

import java.util.List;

public interface UserService  {
    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

    List<msgVO> getUserMsg();
}

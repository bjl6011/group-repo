package com.itheima.service;

import com.itheima.pojo.Manager;
import com.itheima.pojo.User;

import java.util.List;

public interface ManagerService {

    Manager fingByManagerName(String managerName);

    List<User> initialize(Integer pageNumber, Integer pageSize);

    Integer totalNum();

    List<User> searchByOption(String optionSelect,String optionValue,Integer pageNumber, Integer pageSize);

    List<User> details(Integer id);

    Integer selectNum(String optionSelect,String optionValue);

    void addUser(String username, String gender, String password, String nickname, String email);

    void importUser(String username, String gender, String password, String nickname, String email);

    void updateUser(Integer id, String username, String sex, String password, String nickname, String email);

    void deleteUser(Integer id);
}

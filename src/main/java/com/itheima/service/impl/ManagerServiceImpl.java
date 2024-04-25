package com.itheima.service.impl;

import com.itheima.mapper.ManagerMapper;
import com.itheima.pojo.Manager;
import com.itheima.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerMapper managerMapper;
    @Override
    public Manager fingByManagerName(String managerName) {
        Manager manager = managerMapper.findByUserName(managerName);
        return manager;
    }

}

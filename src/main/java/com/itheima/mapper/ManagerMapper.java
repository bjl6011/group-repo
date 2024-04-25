package com.itheima.mapper;

import com.itheima.pojo.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ManagerMapper {
    @Select("select * from sys.manager where username=#{username}")
    Manager findByUserName(String username);
}

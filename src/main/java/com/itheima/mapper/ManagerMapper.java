package com.itheima.mapper;

import com.itheima.pojo.Manager;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ManagerMapper {
    @Select("select * from sys.manager where username=#{username}")
    Manager findByUserName(String username);

    @Select("select * from user limit #{pageNumber},#{pageSize}")//分页显示数据
    List<User> initialize(@Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize);

    @Select("select count(*) from user;")
    Integer totalNum();

    @Select("select * from user where id =#{id}")
    List<User> details(@Param("id") Integer id);

    @Select("select * from user where ${optionSelect} = #{optionValue} limit #{pageNumber},#{pageSize}")//按查找项查找
    List<User> searchByOption(@Param("optionSelect") String optionSelect, @Param("optionValue") Object optionValue,
                              @Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize);

    @Select("select count(*) from user where ${optionSelect} = #{optionValue}")
    Integer selectNum(@Param("optionSelect") String optionSelect, @Param("optionValue") String optionValue);


    @Insert("insert into user(username,sex, password, nickname, email,create_time,update_time)values (#{username},#{sex},#{password}, #{nickname}, #{email},now(), now())")
    void addUser(@Param("username") String username, @Param("sex") String sex, @Param("password") String password,
                 @Param("nickname") String nickname, @Param("email") String email);

    @Insert("insert into user(username,sex, password, nickname, email)values (#{username},#{sex},#{password}, #{nickname}, #{email})")
    void importUser(@Param("username") String username, @Param("sex") String sex, @Param("password") String password,
                    @Param("nickname") String nickname, @Param("email") String email);

    @Update("update user set username = #{username},sex = #{sex}, password = #{password}, nickname = #{nickname}, email = #{email} ,update_time=now() where id = #{id}")
    void updateUser(@Param("id") Integer id, @Param("username") String username, @Param("sex")String sex, @Param("password") String password,
                    @Param("nickname") String nickname, @Param("email") String email);

    @Update("delete from user where id = #{id}")
    void deleteUser(@Param("id") Integer id);


}

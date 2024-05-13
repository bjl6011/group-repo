package com.itheima.administer.mapper;

import com.itheima.administer.pojo.Manager;
import com.itheima.pojo.Feed;
import com.itheima.pojo.New;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ManagerMapper {
    @Select("select * from vue.manager where username = #{username}")
    Manager findByUserName(String username);

    @Select("select * from vue.user limit #{pageNumber},#{pageSize}")//分页显示数据
    List<User> initialize(@Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize);

    @Select("select count(*) from vue.user;")
    Integer totalNum();

    @Select("select * from vue.user where ${optionSelect} = #{optionValue} limit #{pageNumber},#{pageSize}")//按查找项查找
    List<User> searchByOption(@Param("optionSelect") String optionSelect, @Param("optionValue") Object optionValue,
                              @Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize);

    @Select("select count(*) from vue.user where ${optionSelect} = #{optionValue}")
    Integer selectNum(@Param("optionSelect") String optionSelect, @Param("optionValue") String optionValue);


    @Insert("insert into vue.user(username, password, nickname, email,create_time,update_time)values (#{username},#{password}, #{nickname}, #{email},now(), now())")
    void addUser(@Param("username") String username, @Param("sex") String sex, @Param("password") String password,
                 @Param("nickname") String nickname, @Param("email") String email);

    @Insert("insert into vue.user(username, password, nickname, email)values (#{username},#{password}, #{nickname}, #{email})")
    void importUser(@Param("username") String username, @Param("sex") String sex, @Param("password") String password,
                    @Param("nickname") String nickname, @Param("email") String email);

    @Update("update vue.user set username = #{username}, password = #{password}, nickname = #{nickname}, email = #{email} ,update_time=now() where id = #{id}")
    int updateUser(@Param("id") Integer id, @Param("username") String username, @Param("sex")String sex, @Param("password") String password,
                    @Param("nickname") String nickname, @Param("email") String email);

    @Update("delete from vue.user where id = #{id}")
    int deleteUser(@Param("id") Integer id);

    @Select("select * from vue.news where title = #{title}")
    int getNewsByTitle(String title);

    @Insert("insert into vue.news(title, subject, userid, image, create_time, content) " +
            "values (#{title}, #{subject}, #{userid}, #{image}, now(), #{content})")
    int publishNews(New news);

    @Select("select * from vue.feeds where audit_status = #{auditStatus}")
    List<Feed> getFeedsByAuditStatus(int auditStatus);

    @Update("update vue.feeds set audit_status = 1 where id = #{id} and audit_status = 0")
    int updateFeedAudit(int id);

    @Delete("delete from vue.feeds where id = #{id}")
    int deleteFeed(int id);

    @Select("select * from vue.feeds where id = #{id}")
    Feed getFeedById(int id);
}

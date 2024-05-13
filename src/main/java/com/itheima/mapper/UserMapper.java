package com.itheima.mapper;

import com.itheima.pojo.Like;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select *from vue.user where id=#{id}")
    User findByUserId(Integer id);
    @Select("select * from vue.user where username=#{username}")
    User findByUserName(String username) ;

    @Insert("insert into vue.user (username,password,create_time,update_time)"+
    "values(#{username},#{password},now(),now())")
    void add(String username, String password) ;

    @Update("update vue.user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update vue.user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update("update vue.user set password=#{md5String},update_time=now() where id=#{id}")
    void updatePwd(String md5String,Integer id);

    @Select("select id, nickname, user_pic\n" +
            "from vue.user\n" +
            "where id in\n" +
            "(\n" +
            "    select user_id\n" +
            "    from vue.liked\n" +
            "    where target_id = #{targetId}\n" +
            ")")
    List<User> getUserLikes(int targetId);

    @Select("select * from vue.liked where target_id = #{targetId}")
    List<Like> getLikeListByTargetId(int targetId);

}

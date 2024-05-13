package com.itheima.mapper;


import com.itheima.pojo.New;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewMapper {
    @Select("select *from news")
    List<New> list();

    @Select("select *from vue.news where id=#{id}")
    New findById(Integer id);

    @Select("select sum(id) from vue.comment where commentable_id=#{id} and commentable_type='new' ")
    Integer getNewCommentCount(Integer id);

    @Select("select userid from vue.news where id=#{id}")
    int findUserById(Integer id);

    @Insert("insert into vue.liked (user_id,target_id,likeable_id,type,create_time)"+
            "values(#{userid},#{targetId},#{id},'new',now())")
    void like(Integer userid, int targetId, Integer id);

    @Update("update vue.news set like_count=like_count+1 where id=#{id}")
    void addlike(Integer id);

    @Delete("delete from vue.liked where user_id =#{userId} and target_id =#{targetId} and likeable_id=#{id} and type='new'")
    void unlike(Integer userId, int targetId, Integer id);

    @Update("update vue.news set like_count=like_count-1 where id=#{id}")
    void offlike(Integer id);

    @Insert("insert into vue.comment (user_id,target_id,body,commentable_id,commentable_type,create_time)"+
            "values(#{userId},#{targetId},#{body},#{id},'new',now())")
    void comment(Integer userId, int targetId, Integer id, String body);

    @Update("update vue.news set comment_count=comment_count+1 where id=#{id}")
    void addComment(Integer id);

    @Update("update vue.news " +
            "set title = #{title}, subject = #{subject}, content = #{content}, image = #{image} " +
            "where id = #{id}")
    int updateNews(int id, String title, String subject, String content, String image);

    @Delete("delete from vue.news where id = #{id}")
    int deleteNews(int id);

}

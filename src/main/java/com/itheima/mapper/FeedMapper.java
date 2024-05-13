package com.itheima.mapper;

import com.itheima.entity.FeedVO;
import com.itheima.pojo.Feed;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FeedMapper {

    @Select("select * from vue.feeds where audit_status=1 ORDER BY create_time DESC")
    List<Feed> list();

    @Select("select * from vue.feeds where audit_status=0 ORDER BY create_time DESC")
    List<Feed> listUnaudit();
    @Select("select * from vue.feeds where audit_status=1 and id=#{id} ")
    Feed getById(Integer id);

    @Select("select * from vue.feeds where user_id=#{userid}")
    List<Feed> listByUser(Integer userid);

    @Insert("insert into vue.liked (user_id,target_id,likeable_id,type,create_time)"+
            "values(#{userId},#{targetId},#{id},'feed',now())")
     void like(Integer userId,Integer targetId, Integer id);

    @Delete("delete from vue.liked where user_id =#{userId} and target_id =#{targetId} and likeable_id=#{id} and type='feed'")
    void unlike(Integer userId, int targetId, Integer id);

    @Select("select user_id from vue.feeds where id=#{id}")
    int findUserById(Integer id);


    @Select("select * from vue.liked where user_id=#{userId} and likeable_id=#{id}")
    Feed hasLike(Integer userId, Integer id);

    @Update("update vue.feeds set like_count=like_count+1 where id=#{id}")
    void addlike(Integer id);

    @Update("update vue.feeds set like_count=like_count-1 where id=#{id}")
    void offlike(Integer id);

    @Update("update vue.feeds set comment_count=comment_count+1 where id=#{id}")
    void addComment(Integer id);

    @Insert("insert into vue.comment (user_id,target_id,body,commentable_id,commentable_type,create_time)"+
            "values(#{userId},#{targetId},#{body},#{id},'feed',now())")
    void comment(Integer userId, int targetId, Integer id, String body);

    @Insert("insert into vue.feeds (user_id,feed_content,images,create_time)" +
            "values(#{userId},#{content},#{images},now())")
    void addFeed(Integer userId, String content, String images);

    @Update("update vue.feeds set feed_view_count=feed_view_count+1 where id=#{id}")
    void addViewCount(Integer id);

    @Select("select images from vue.feeds where id = #{id}")
    String getFeedImage(Integer id);
}

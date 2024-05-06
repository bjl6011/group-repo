package com.itheima.mapper;

import com.itheima.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select *from sys.comment where commentable_id=#{id} and commentable_type='feed'")
    List<Comment> getFeedComments(Integer id);

    @Select("select * from sys.comment where target_id = #{id} and commentable_type = 'feed'")
    List<Comment> getFeedCommentByTargetId(Integer id);


    @Select("select *from comment where commentable_id=#{id} and commentable_type='new'")
    List<Comment> getNewComments(Integer id);

    @Insert("insert into comment (user_id,target_id,body,commentable_id,commentable_type,create_time)" +
            "values(#{userId},#{targetId},#{body},#{commentableId},'feed',now())")
    void add(Integer userId,Integer targetId,String body,Integer commentableId);


}

package com.itheima.mapper;

import com.itheima.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into vue.article (title,content,cover_img,state,category_id,create_user,create_time,update_time)" +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);


    List<Article> list(Integer userId, String categoryId, String state);

    @Update("update vue.article set title=#{title},content=#{content}," +
            "cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id=#{id}")
    void update(Article article);

    @Select("select *from vue.article where id=#{id}")
    Article findById(Integer id);

    @Delete("delete from vue.article where id=#{id}")
    void delete(Integer id);
}

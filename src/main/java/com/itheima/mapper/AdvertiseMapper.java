package com.itheima.mapper;

import com.itheima.pojo.Advertise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdvertiseMapper {

    @Select("select * from vue.advertise where id=#{id}")
    Advertise getAdvert(Integer id);
}

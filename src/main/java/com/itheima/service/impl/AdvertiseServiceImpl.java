package com.itheima.service.impl;

import com.itheima.mapper.AdvertiseMapper;
import com.itheima.pojo.Advertise;
import com.itheima.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {
    @Autowired
    private AdvertiseMapper advertiseMapper;
    @Override
    public List<Advertise> getAdvert(List<Integer> spaces) {
        List<Advertise> advertises=new ArrayList<>();
        for(int i=0;i<spaces.size();i++){
            advertises.add(advertiseMapper.getAdvert(spaces.get(i)));
        }
        return advertises;
    }
}

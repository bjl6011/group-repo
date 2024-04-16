package com.itheima.service;

import com.itheima.pojo.Advertise;

import java.util.List;

public interface AdvertiseService {
    List<Advertise> getAdvert(List<Integer> spaces);
}

package com.itheima.controller;


import com.itheima.pojo.Advertise;
import com.itheima.pojo.Result;
import com.itheima.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
public class AdvertiseController {
    @Autowired
    private AdvertiseService advertiseService;

    @GetMapping("/advertisingspace")
    public Result<List<Advertise>> getAdvert(@RequestParam("spaces") List<Integer> spaces){
        List<Advertise> ads=advertiseService.getAdvert(spaces);

        return Result.success(ads);
    }

    @GetMapping("/advertise")
    public Result<List<Advertise>> get(){
        List<Advertise> ads=advertiseService.getAdvert(null);

        return Result.success(ads);
    }

}

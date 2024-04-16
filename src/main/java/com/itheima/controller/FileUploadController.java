package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        //文件名字唯一
        String filename= UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));

//        file.transferTo(new File("C:\\Users\\Administrator\\Desktop\\files\\"+filename));

        String url=AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);
    }


}

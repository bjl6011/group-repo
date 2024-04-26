package com.itheima.controller;

import com.itheima.DTO.loginDTO;
import com.itheima.pojo.*;
import com.itheima.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 管理员登录
    @PostMapping("/managerLogin")
    public Result managerLogin(@RequestBody loginDTO loginDTO) {

        Manager manager = managerService.fingByManagerName(loginDTO.getUsername());
        if(manager == null) {
            return Result.error2("用户名错误");
        }
        else {
            if(loginDTO.getPassword().equals(manager.getPassword())) {

                return Result.success("登陆成功");
            }
            else {
                return Result.error2("密码错误");
            }
        }
    }

    // 用户信息列表
    @GetMapping("/initialize")//初始化界面
    public Map<String,Object> initialize(@RequestParam("pageNumber") Integer pageNumber,
                                         @RequestParam("pageSize") Integer pageSize){
        pageNumber=(pageNumber-1)*pageSize;
        List<User> datas = managerService.initialize(pageNumber,pageSize);
        Integer totalNum = managerService.totalNum();
        Map<String,Object> res=new HashMap<>();
        res.put("data",datas);
        res.put("total",totalNum);
        return res;
    }

    @GetMapping("/details")
    public List<String> details(@RequestParam("userId") Integer id){
        List<User> detail=managerService.details(id);
        List<String>times=new ArrayList<>();
        for (User user : detail) {
            String create_time=String.valueOf(user.getCreateTime());
            String update_time=String.valueOf(user.getUpdateTime());
            String[] create = create_time.split("\\.");
            String createTime = create[0];
            String[] update = update_time.split("\\.");
            String updateTime = update[0];
            times.add(createTime);
            times.add(updateTime);
        }
        return times;
    }

    @PostMapping("/search")//查找
    public Map<String,Object> searchByOption(@RequestBody Option option) {
        List<User> datas = managerService.searchByOption(option.getOptionSelect(), option.getOptionValue(),
                (option.getPageNumber()-1)*option.getPageSize(),option.getPageSize());
        Integer totalNum = managerService.selectNum(option.getOptionSelect(),option.getOptionValue());
        Map<String,Object> res=new HashMap<>();
        res.put("data",datas);
        res.put("total",totalNum);
        return res;
    }

    @PostMapping("/add")//新增
    public void addUser(@RequestBody User user){
        managerService.addUser(user.getUsername(),user.getSex(),user.getPassword(),user.getNickname(),user.getEmail());
    }

    @PostMapping("/upload")//导入
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Failed to upload file: File is empty";
        }

        try (Scanner scanner = new Scanner(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            // 跳过第一行标题行
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // 处理CSV文件中的数据
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String username = data[0];
                    String sex =data[1];
                    String password = data[2];
                    String nickname = data[3];
                    String email = data[4];
                    managerService.importUser(username,sex, password, nickname, email);
                } else {
                    // 可能需要处理数据不完整的情况
                    return "Failed to process file: Data is incomplete";
                }
            }
            return "File uploaded successfully and data processed.";
        } catch (IOException e) {
            return "Failed to upload or process file: " + e.getMessage();
        }
    }

    @PostMapping("/edit")
    public void editUser(@RequestBody User user){
        managerService.updateUser(user.getId(),user.getUsername(),user.getSex(),user.getPassword(),user.getNickname(),user.getEmail());
    }

    @PostMapping("/delete")
    public void deleteUser(@RequestBody User user){
        managerService.deleteUser(user.getId());
    }

    @PostMapping("/deletes")
    public void deleteUsers(@RequestBody Users users){ // Users

        ArrayList<Integer> selectedId = users.getSelectedId();
        for (Integer id : selectedId) {
            managerService.deleteUser(id);
        }
    }

}

package com.itheima.administer.controller;

import com.itheima.DTO.NewsDTO;
import com.itheima.DTO.loginDTO;
import com.itheima.administer.entity.UserTimeVO;
import com.itheima.administer.pojo.Manager;
import com.itheima.administer.pojo.Option;
import com.itheima.administer.pojo.Users;
import com.itheima.entity.FeedVO;
import com.itheima.mapper.FeedMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.*;
import com.itheima.administer.service.ManagerService;
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
    private UserMapper userMapper;

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
    // 初始化界面
    @GetMapping("/initialize")
    public Result<Map<String,Object>> initialize(@RequestParam("pageNumber") Integer pageNumber,
                                         @RequestParam("pageSize") Integer pageSize){

        Map<String,Object> res=new HashMap<>();

        if(pageNumber >= 1) {
            pageNumber = (pageNumber - 1) * pageSize;
            List<User> data = managerService.initialize(pageNumber,pageSize);
            Integer totalNum = managerService.totalNum();

            res.put("data",data);
            res.put("total",totalNum);
        }

        return Result.success(res);
    }

    // 详细时间
    @GetMapping("/details")
    public Result<UserTimeVO> details(@RequestParam("userId") Integer id){
        return managerService.userDetails(id);
    }

    // 查找用户
    @PostMapping("/search")
    public Result<Map<String,Object>> searchByOption(@RequestBody Option option) {

        List<User> data = managerService.searchByOption(option.getOptionSelect(), option.getOptionValue(),
                (option.getPageNumber()-1)*option.getPageSize(),option.getPageSize());
        Integer totalNum = managerService.selectNum(option.getOptionSelect(),option.getOptionValue());
        Map<String,Object> res=new HashMap<>();
        res.put("data",data);
        res.put("total",totalNum);

        return Result.success(res);
    }

    // 新增用户
    @PostMapping("/add")
    public Result addUser(@RequestBody User user){
        String username = user.getUsername();

        if(username != null) {
            return managerService.addUser(user.getUsername(),user.getSex(),user.getPassword(),user.getNickname(),user.getEmail());
        }
        else {
            return Result.error("用户名为空！");
        }

    }

    // 导入文件 XXX
    @PostMapping("/upload")
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


    // 编辑用户信息
    @PostMapping("/edit")
    public Result editUser(@RequestBody User user){

        return managerService.updateUser(user.getId(),user.getUsername(),user.getSex(),
                user.getPassword(),user.getNickname(),user.getEmail());

    }

    // 删除用户
    @PostMapping("/delete")
    public Result deleteUser(@RequestBody User user){

        return managerService.deleteUser(user.getId());
    }

    // 删除多用户
    @PostMapping("/deletes")
    public List<Result> deleteUsers(@RequestBody Users users){ // Users

        ArrayList<Integer> selectedId = users.getSelectedId();
        List<Result> res = new ArrayList<>();

        for (Integer id : selectedId) {
            res.add(managerService.deleteUser(id));
        }

        return res;
    }


    // 发布咨询
    @PostMapping("/publishNews")
    public Result publishNews(@RequestBody New news) {
        return managerService.publishNews(news);
    }


    // 更新资讯
    @PostMapping("/updateNews")
    public Result updateNews(@RequestBody NewsDTO news) {
        int newsId = news.getId();
        String subject = news.getSubject();
        String content = news.getContent();
        String title = news.getTitle();
        String image = news.getImage();

        return managerService.updateNews(newsId, title, subject, content, image);
    }

    // 删除资讯
    @DeleteMapping("/deleteNews")
    public Result deleteNews(@RequestParam("id") Integer id) {
        return managerService.deleteNews(id);
    }

    // 上传图像文件到OSS
    @GetMapping("/imageUpload")
    public Result imageUpload(@RequestParam("file") MultipartFile file) throws Exception {
        return managerService.imageUpload(file);
        // url
    }


    // 审核或未审核list
    @GetMapping("/getFeedsByAuditStatus")
    public Result<List<Feed>> getFeedsByAuditStatus(@RequestParam("auditStatus") int status) {
        return managerService.getFeedsByAuditStatus(status);
    }

    // 未审核的feed list
    @GetMapping("/getUnauditFeeds")
    public Result<List<FeedVO>> getUnauditFeeds() {
        return managerService.getUnauditFeeds();
    }

    // 已审核的feed list
    @GetMapping("/getAuditFeeds")
    public Result<List<FeedVO>> getAuditFeeds() {
        return managerService.getAuditFeeds();
    }

    // 审核feed通过
    @GetMapping("/updateFeedAudit")
    public Result updateFeedAudit(@RequestParam("id") int id) {
        return managerService.updateFeedAudit(id);
    }


    // feed详细查看
    @GetMapping("/getFeedById")
    public Result<FeedVO> getFeedById(@RequestParam("id") int id) {
        return managerService.getFeedById(id);
    }

    // feed删除
    @GetMapping("/deleteFeedById")
    public Result deleteFeedById(@RequestParam("id") int id) {
        return managerService.deleteFeedById(id);
    }

}

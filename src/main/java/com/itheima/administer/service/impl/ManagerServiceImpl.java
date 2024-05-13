package com.itheima.administer.service.impl;

import com.itheima.administer.entity.UserTimeVO;
import com.itheima.administer.mapper.ManagerMapper;
import com.itheima.assembler.FeedAssembler;
import com.itheima.controller.FileUploadController;
import com.itheima.entity.FeedVO;
import com.itheima.mapper.FeedMapper;
import com.itheima.mapper.NewMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.administer.pojo.Manager;
import com.itheima.pojo.Feed;
import com.itheima.pojo.New;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.administer.service.ManagerService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private NewMapper newMapper;

    @Autowired
    private FeedMapper feedMapper;

    private FeedAssembler feedAssembler=new FeedAssembler();

    @Override
    public Manager fingByManagerName(String managerName) {
        Manager manager = managerMapper.findByUserName(managerName);
        return manager;
    }

    @Autowired
    public void UserService(ManagerMapper managerMapper) {
        this.managerMapper = managerMapper;
    }

    public List<User> initialize(Integer pageNumber, Integer pageSize){
        return managerMapper.initialize(pageNumber,pageSize);
    }

    public Integer totalNum(){
        return managerMapper.totalNum();
    }

    public List<User> searchByOption(String optionSelect,String optionValue,Integer pageNumber, Integer pageSize){
        return managerMapper.searchByOption(optionSelect,optionValue,pageNumber,pageSize);
    }

    public Result<UserTimeVO> userDetails(Integer id) {

        User user = userMapper.findByUserId(id);
        UserTimeVO userTimeVO = new UserTimeVO();
        if(user != null) {
            // user存在
            userTimeVO.setCreateTime(user.getCreateTime());
            userTimeVO.setUpdateTime(user.getUpdateTime());
        }

        return Result.success(userTimeVO);

    }

    public Integer selectNum(String optionSelect,String optionValue){
        return managerMapper.selectNum(optionSelect,optionValue);
    }

    public Result addUser(String username, String gender, String password, String nickname, String email){
        if(username.equals(userMapper.findByUserName(username).getUsername())) {
            return Result.error("该用户已存在！");
        }
        else {
            managerMapper.addUser(username,gender,password,nickname,email);
            return Result.success("用户添加成功！");
        }

    }

    public void importUser(String username, String gender, String password, String nickname, String email){
        managerMapper.importUser(username,gender,password,nickname,email);
    }

    public Result updateUser(Integer id, String username, String sex, String password, String nickname, String email){

        int operatorNum = managerMapper.updateUser(id,username,sex,password,nickname,email);
        if(operatorNum == 0) {
            return Result.error(id.toString() + "未找到该用户!");
        }
        else {
            return Result.success(id.toString() + "更新成功！");
        }
    }

    public Result deleteUser(Integer id){

        int operatorNum = managerMapper.deleteUser(id);
        if(operatorNum == 0) {
            return Result.error(id.toString() + "未找到该用户!");
        }
        else {
            return Result.success(id.toString() + "删除成功！");
        }

    }


    public Result updateNews(int id, String title, String subject, String content, String image) {

        if(newMapper.findById(id) == null) {
            return Result.error("找不到该资讯！");
        }
        else {
            int isSuccess  = newMapper.updateNews(id, title, subject, content, image);
            if(isSuccess == 1) {
                return Result.error("资讯更新成功！");
            }
            else {
                return Result.error("咨询更新失败！");
            }
        }
    }

    public Result deleteNews(int id) {
        if(newMapper.findById(id) == null) {
            return Result.error("找不到该资讯！");
        }
        else {
            int isSuccess  = newMapper.deleteNews(id);
            if(isSuccess == 1) {
                return Result.success("资讯删除成功！");
            }
            else {
                return Result.success("资讯删除失败！");
            }
        }
    }

    public Result publishNews(New news) {
        int newsCnt = managerMapper.getNewsByTitle(news.getTitle());
        if( newsCnt > 0) {
            return Result.error("该咨询已存在！");
        }
        else {
            if(managerMapper.publishNews(news) > 0) {
                return Result.success("发布成功！");
            }
            else {
                return Result.error("发布失败！");
            }
        }
    }

    public Result<String> imageUpload(MultipartFile file) throws Exception {

        FileUploadController fileUploadController = new FileUploadController();
        if(file.isEmpty()) {
            return Result.error("图像文件出错！");
        }
        else {
            return fileUploadController.upload(file);
        }

    }

    public Result<List<Feed>> getFeedsByAuditStatus(int status) {

        if(status == 0 || status == 1) {
            return Result.success(managerMapper.getFeedsByAuditStatus(status));
        }
        else {
            List<Feed> list = new ArrayList<>();
            return Result.error(list, "审核状态不合规！！");
        }

    }

    public Result<List<FeedVO>> getUnauditFeeds() {

        List<Feed> feeds=feedMapper.listUnaudit();
        return getListResult(feeds);
    }

    public Result<List<FeedVO>> getAuditFeeds() {

        List<Feed> feeds=feedMapper.list();
        return getListResult(feeds);
    }

    private Result<List<FeedVO>> getListResult(List<Feed> feeds) {
        List<FeedVO> feedVOS=new ArrayList<>();
        for(int i=0;i<feeds.size();i++){
            User user=userMapper.findByUserId(feeds.get(i).getUserID());
            boolean hasLike= true;
            feedVOS.add(feedAssembler.assembler(feeds.get(i),user,hasLike));
        }

        return Result.success(feedVOS);
    }


    public Result updateFeedAudit(int id) {

        Feed feed = managerMapper.getFeedById(id);
        if(feed != null && feed.getAuditStatus() == 1) {
            return Result.error("该动态已审核通过！");
        }
        else {
            if(managerMapper.updateFeedAudit(id) == 1) {
                return Result.success("动态审核通过！");
            }
            else {
                return Result.error("审核失败！");
            }
        }
    }

    public Result<FeedVO> getFeedById(int id) {

        Feed feed = managerMapper.getFeedById(id);
        FeedVO feedVO = new FeedVO();
        if(feed != null) {
            User user=userMapper.findByUserId(feed.getUserID());
            feedVO = feedAssembler.assembler(feed, user, true);
            return Result.success(feedVO);
        }
        else {
            return Result.error(feedVO,"该动态不存在!");
        }
    }

    public Result deleteFeedById(int id) {
        if(managerMapper.getFeedById(id) == null) {
            return Result.error("找不到该动态！");
        }
        else {
            int isSuccess  = managerMapper.deleteFeed(id);
            if(isSuccess == 1) {
                return Result.success("动态删除成功！");
            }
            else {
                return Result.success("动态删除失败！");
            }
        }
    }
}

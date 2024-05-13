package com.itheima.administer.service;

import com.itheima.administer.entity.UserTimeVO;
import com.itheima.administer.pojo.Manager;
import com.itheima.entity.FeedVO;
import com.itheima.pojo.Feed;
import com.itheima.pojo.New;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ManagerService {

    Manager fingByManagerName(String managerName);

    List<User> initialize(Integer pageNumber, Integer pageSize);

    Integer totalNum();

    List<User> searchByOption(String optionSelect,String optionValue,Integer pageNumber, Integer pageSize);

    Result<UserTimeVO> userDetails(Integer id);

    Integer selectNum(String optionSelect,String optionValue);

    Result addUser(String username, String gender, String password, String nickname, String email);

    void importUser(String username, String gender, String password, String nickname, String email);

    Result updateUser(Integer id, String username, String sex, String password, String nickname, String email);

    Result deleteUser(Integer id);

    Result updateNews(int id, String title, String subject, String content, String image);

    Result deleteNews(int id);

    Result publishNews(New news);

    Result<String> imageUpload(MultipartFile file) throws Exception;


    Result<List<Feed>> getFeedsByAuditStatus(int status);

    Result updateFeedAudit(int id);

    Result<FeedVO> getFeedById(int id);

    Result deleteFeedById(int id);


    Result<List<FeedVO>> getUnauditFeeds();
    Result<List<FeedVO>> getAuditFeeds();

}

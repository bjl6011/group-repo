package com.itheima.service.impl;

import com.itheima.assembler.FeedAssembler;
import com.itheima.entity.FeedVO;
import com.itheima.entity.msgVO;
import com.itheima.mapper.CommentMapper;
import com.itheima.mapper.FeedMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Comment;
import com.itheima.pojo.Feed;
import com.itheima.pojo.Like;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private FeedMapper feedMapper;

    private FeedAssembler feedAssembler=new FeedAssembler();

    @Override
    public User findByUserName(String username) {
        User user=userMapper.findByUserName(username);

        return user;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String=Md5Util.getMD5String(password);
        //添加
        userMapper.add(username,md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id=(Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id=(Integer) map.get("id");

        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }

    @Override
    public List<msgVO> getUserMsg() {

        List<msgVO> msgVOS = new ArrayList<>();
        // 获取当前登录用户信息
        Map<String,Object> map = ThreadLocalUtil.get();
        // String userId=(String)map.get("id");
        String userId = "2";
        if(userId == null) {
            // 未登录
            return msgVOS;
        } else {
            // 登录成功

            int targetId = Integer.parseInt(userId);

            // 获取点赞列表
            List<Like> likeList = userMapper.getLikeListByTargetId(targetId);
            // 获取评论列表
            // getFeedComments  !!!!!!!!!
            List<Comment> commentList =commentMapper.getFeedCommentByTargetId(targetId);

            // 遍历点赞列表
            for(Like like : likeList) {
                msgVO likeUser = new msgVO();

                int id = like.getUserID();
                User user = userMapper.findByUserId(id);
                // 点赞人头像
                likeUser.setUserPic(user.getUserPic());
                // 点赞人昵称
                likeUser.setNickname(user.getNickname());
                // 点赞时间
                likeUser.setTime(like.getCreateTime());
                // 内容
                String content = user.getNickname() + "赞了您的动态!";
                likeUser.setContent(content);

                // 动态封面
                Integer likeableId = like.getLikeableId();
                Feed feed = feedMapper.getById(likeableId);
                String cover = feed.getImages().split(",")[0];
                likeUser.setCover(cover);

                msgVOS.add(likeUser);
            }

            // 遍历评论列表
            for(Comment comment : commentList) {
                msgVO commentUser = new msgVO();
                int id = comment.getUserId();
                User user = userMapper.findByUserId(id);

                // 评论人头像
                commentUser.setUserPic(user.getUserPic());
                // 评论人昵称
                commentUser.setNickname(user.getNickname());
                // 评论时间
                commentUser.setTime(comment.getCreateTime());
                // 评论内容
                commentUser.setContent(comment.getBody());
                // 封面
                Integer commentableId = comment.getCommentableId();
                Feed feed = feedMapper.getById(commentableId);
                String cover = feed.getImages().split(",")[0];
                commentUser.setCover(cover);

                msgVOS.add(commentUser);
            }

            // 按时间排序
            msgVOS = msgVOS.stream()
                    .sorted(Comparator.comparing(msgVO::getTime).reversed())
                    .collect(Collectors.toList());

            return msgVOS;

        }
    }
}

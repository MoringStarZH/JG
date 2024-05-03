package com.example.jg.Service.ServiceImpl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jg.Mapper.UserMapper;
import com.example.jg.Pojo.User;
import com.example.jg.Service.UserService;
import com.example.jg.Utils.WeChatLoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @title ServiceImpl
 * @Author: ZKY
 * @CreateTime: 2023-12-25  17:00
 * @Description: TODO
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User wxLogin(String code) {
        JSONObject responseJson = WeChatLoginUtil.getResponseJson(code);
        log.info("responseJson: {}",responseJson.toString());
        String openid = responseJson.getString("openid");

        User user = userMapper.selectByOpenId(openid);
        if (user == null){
            user = new User();
            user.setOpenId(openid);
            user.setNickName("微信用户");
            userMapper.insert(user);
        }
        return user;
    }
}

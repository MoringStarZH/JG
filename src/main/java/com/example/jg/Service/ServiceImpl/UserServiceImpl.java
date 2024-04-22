package com.example.jg.Service.ServiceImpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jg.Mapper.UserMapper;
import com.example.jg.Pojo.User;
import com.example.jg.Service.UserService;
import org.springframework.stereotype.Service;

/**
 * @title ServiceImpl
 * @Author: ZKY
 * @CreateTime: 2023-12-25  17:00
 * @Description: TODO
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

package com.example.gfjc.Service.ServiceImpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gfjc.Mapper.UserMapper;
import com.example.gfjc.Pojo.User;
import com.example.gfjc.Service.UserService;
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

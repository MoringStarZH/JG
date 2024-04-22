package com.example.jg.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jg.Mapper.AuthorityMapper;
import com.example.jg.Pojo.Authority;
import com.example.jg.Service.AuthorityService;
import org.springframework.stereotype.Service;

/**
 * @title LimitServiceImpl
 * @Author: ZKY
 * @CreateTime: 2024-04-01  16:04
 * @Description: TODO
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {
}

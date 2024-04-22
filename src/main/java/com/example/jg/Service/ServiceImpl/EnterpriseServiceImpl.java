package com.example.jg.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jg.Mapper.EnterpriseMapper;
import com.example.jg.Pojo.Enterprise;
import com.example.jg.Service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title EnterpriseServiceImpl
 * @Author: ZKY
 * @CreateTime: 2024-04-01  17:19
 * @Description: TODO
 */
@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements EnterpriseService {
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public List<String> findAllName(){
        return enterpriseMapper.findAllName();
    }
}


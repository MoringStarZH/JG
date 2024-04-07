package com.example.gfjc.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gfjc.Mapper.EnterpriseMapper;
import com.example.gfjc.Pojo.Enterprise;
import com.example.gfjc.Service.EnterpriseService;
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


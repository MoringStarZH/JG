package com.example.gfjc.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gfjc.Mapper.EnterpriseMapper;
import com.example.gfjc.Pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @title EnterpriseService
 * @Author: ZKY
 * @CreateTime: 2024-04-01  17:18
 * @Description: TODO
 */
public interface EnterpriseService extends IService<Enterprise> {
    List<String> findAllName();
}

package com.example.jg.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.jg.Pojo.Enterprise;

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

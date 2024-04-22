package com.example.jg.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.jg.Pojo.SystemStatus;
import com.example.jg.Pojo.User;

/**
 * @title SystemStatusService
 * @Author: ZKY
 * @CreateTime: 2024-04-22  11:21
 * @Description: TODO
 */
public interface SystemStatusService extends IService<SystemStatus> {
    User findWorker();
}

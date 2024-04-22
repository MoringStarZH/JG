package com.example.jg.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.jg.Pojo.Device;

import java.util.List;

/**
 * @title DeviceService
 * @Author: ZKY
 * @CreateTime: 2024-04-08  08:04
 * @Description: TODO
 */
public interface DeviceService extends IService<Device> {
    List<String> findAllName();
}

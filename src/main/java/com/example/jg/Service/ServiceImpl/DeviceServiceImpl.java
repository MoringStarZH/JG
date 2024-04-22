package com.example.gfjc.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gfjc.Mapper.DeviceMapper;
import com.example.gfjc.Pojo.Device;
import com.example.gfjc.Service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title DeviceServiceImpl
 * @Author: ZKY
 * @CreateTime: 2024-04-08  08:05
 * @Description: TODO
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public List<String> findAllName() {
        return deviceMapper.findAllName();
    }
}

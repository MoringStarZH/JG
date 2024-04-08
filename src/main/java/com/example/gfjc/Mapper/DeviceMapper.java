package com.example.gfjc.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.gfjc.Pojo.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @title DeviceMapper
 * @Author: ZKY
 * @CreateTime: 2024-04-08  08:04
 * @Description: TODO
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
    @Select("SELECT name FROM device")
    List<String> findAllName();
}

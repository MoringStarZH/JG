package com.example.jg.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jg.Pojo.Device;
import com.example.jg.Service.DeviceService;
import com.example.jg.common.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @title DeviceController
 * @Author: ZKY
 * @CreateTime: 2024-04-08  08:06
 * @Description: TODO
 */
@RestController
@Slf4j
@RequestMapping("/device")
@CrossOrigin
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @ApiOperation("新增设备")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Device device){
        device.setStatus(1);
        if (!deviceService.save(device)){
            return Result.error("出现错误");
        }
        return Result.error("设备新增成功");
    }

    @ApiOperation("设备分页查询")
    @GetMapping("/page")
    public Result<Page<Device>> page(Integer page, Integer pageSize, String name){
        log.info("page = {}, pageSize = {}",page,pageSize);
        Page<Device> pageInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Device> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Device::getName,name);
        queryWrapper.orderByDesc(Device::getUpdateTime);


        deviceService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    @ApiOperation("设备信息的更新")
    @PostMapping("/update")
    public Result<String> update(@RequestBody Device device){
        if (!deviceService.updateById(device)){
            return Result.error("出现错误");
        }
        return Result.success("更新成功");
    }

    @ApiOperation("删除设备")
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        if (!deviceService.removeById(id)){
            return Result.error("出先错误");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("获取所有设备的名称")
    @GetMapping("/findAllName")
    public Result<List<String>> findAllName(){
        List<String> list = deviceService.findAllName();
        return Result.success(list);
    }

}

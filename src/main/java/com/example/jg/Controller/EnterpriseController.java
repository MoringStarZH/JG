package com.example.jg.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jg.Pojo.Enterprise;
import com.example.jg.Service.EnterpriseService;
import com.example.jg.common.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @title Enterprise
 * @Author: ZKY
 * @CreateTime: 2024-04-01  17:20
 * @Description: TODO
 */
@RestController
@Slf4j
@RequestMapping("/enterprise")
@CrossOrigin
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    @ApiOperation("新增企业")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Enterprise enterprise){
        log.info(enterprise.toString());
        enterprise.setStatus(1);
        enterpriseService.save(enterprise);
        return Result.success("企业新增成功");
    }

    @ApiOperation("更新企业信息")
    @PostMapping("/update/{id}")
    public Result<String> update(@RequestBody Enterprise enterprise,@PathVariable Long id){
        enterprise.setId(id);
        log.info(enterprise.toString());
        enterpriseService.updateById(enterprise);
        return Result.success("信息修改成功");
    }

    @ApiOperation("删除企业")
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable String id){
        enterpriseService.removeById(id);
        return Result.success("删除成功");
    }

    @ApiOperation("企业分页查询")
    @GetMapping("/page")
    public Result<Page<Enterprise>> page(Integer page, Integer pageSize, String name){
        log.info("page = {}, pageSize = {}",page,pageSize);
        Page<Enterprise> pageInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Enterprise> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Enterprise::getName,name);
        queryWrapper.orderByDesc(Enterprise::getUpdateTime);


        enterpriseService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    @ApiOperation("查询所有企业的名称")
    @GetMapping("/name")
    public Result<List<String>> name(){
//        LambdaQueryWrapper<Enterprise> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(Enterprise::getName);
        List<String> list = enterpriseService.findAllName();
        return Result.success(list);
    }

}

package com.example.gfjc.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gfjc.Pojo.Authority;
import com.example.gfjc.Pojo.Enterprise;
import com.example.gfjc.Service.AuthorityService;
import com.example.gfjc.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @title AuthorityController
 * @Author: ZKY
 * @CreateTime: 2024-04-07  17:34
 * @Description: TODO
 */
@RestController
@Slf4j
@RequestMapping("/authority")
@CrossOrigin
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;

    @ApiOperation("权限分页查询")
    @GetMapping("/page")
    public Result<Page<Authority>> page(Integer page, Integer pageSize){
        log.info("page = {}, pageSize = {}",page,pageSize);
        Page<Authority> pageInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Authority> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Authority::getId);


        authorityService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    @ApiOperation("权限的更新")
    @PostMapping("/update")
    public Result<String> update(@RequestBody Authority authority){
        if(!authorityService.updateById(authority)){
            return Result.error("出现错误");
        }
        return Result.success("修改成功");
    }

    @ApiOperation("新增岗位权限")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Authority authority){
        if (!authorityService.save(authority)){
            return Result.error("出现错误");
        }
        return Result.success("新增成功");
    }

}

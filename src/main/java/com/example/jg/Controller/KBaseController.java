package com.example.jg.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jg.Pojo.KBase;
import com.example.jg.Pojo.User;
import com.example.jg.Service.KBaseService;
import com.example.jg.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @title KBaseController
 * @Author: ZKY
 * @CreateTime: 2024-04-25  17:51
 * @Description: TODO
 */
@RestController
@Slf4j
@RequestMapping("/KBase")
@CrossOrigin
public class KBaseController {
    @Autowired
    private KBaseService kBaseService;

    @ApiOperation("知识库新增")
    @PostMapping("/save")
    public Result<String> save(@RequestBody KBase kBase){
        if (!kBaseService.save(kBase)){
            return Result.error("出现错误");
        }
        return Result.success("新增"+kBase.getType()+"成功");
    }

    @ApiOperation("知识库删除")
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        if (!kBaseService.removeById(id)){
            return Result.error("出现错误");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("知识库展示")
    @GetMapping("/display")
    public Result<List<KBase>> display(String type){
        LambdaQueryWrapper<KBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(type), KBase::getType,type);
        queryWrapper.orderByDesc(KBase::getUpdateTime);

        List<KBase> list = kBaseService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation("知识库修改")
    @PostMapping("/update")
    public Result<String> update(@RequestBody KBase kBase){
        if (!kBaseService.updateById(kBase)){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }
}

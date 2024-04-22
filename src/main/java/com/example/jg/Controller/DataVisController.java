package com.example.gfjc.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gfjc.Enum.TypeMap;
import com.example.gfjc.Pojo.User;
import com.example.gfjc.Pojo.VisitsNum;
import com.example.gfjc.Pojo.WorkSheet;
import com.example.gfjc.Service.VisitsNumService;
import com.example.gfjc.Service.WorkSheetService;
import com.example.gfjc.common.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @title DataVisController
 * @Author: ZKY
 * @CreateTime: 2024-04-08  10:11
 * @Description: TODO
 */
@RestController
@Slf4j
@RequestMapping("/DataVis")
@CrossOrigin
public class DataVisController {
    @Autowired
    private VisitsNumService visitsNumService;

    @Autowired
    private WorkSheetService workSheetService;

    @ApiOperation("获取最近七天的系统访问人数")
    @GetMapping("/VisitsNum")
    public Result<Page<VisitsNum>> VisitsNum(){
        LambdaQueryWrapper<VisitsNum> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(VisitsNum::getCreateDate);
        Page<VisitsNum> pageInfo = new Page<>(1,7);
        visitsNumService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }

    @ApiOperation("获取每种缺陷类型的个数")
    @GetMapping("/defectNum")
    public Result<Map<String, Integer>> defect(){
        Map<String, Integer> numMap = TypeMap.typeNumMap;
        Set<String> key = numMap.keySet();
        for(String s : key){
            LambdaQueryWrapper<WorkSheet> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(WorkSheet::getDefectType, s);
            int count = workSheetService.count(lambdaQueryWrapper);
            numMap.put(s,count);
        }
        return Result.success(numMap);
    }

    @ApiOperation("获取风险等级个数")
    @GetMapping("/riskNum")
    public Result<Map<String, Integer>> riskNum(){
        Map<String, Integer> numMap = TypeMap.riskNumMap;
        Set<String> key = numMap.keySet();
        for(String s : key){
            LambdaQueryWrapper<WorkSheet> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(WorkSheet::getRiskLevel, s);
            int count = workSheetService.count(lambdaQueryWrapper);
            numMap.put(s,count);
        }
        return Result.success(numMap);
    }

}

package com.example.gfjc.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gfjc.Enum.WorkSheetStatus;
import com.example.gfjc.Pojo.Picture;
import com.example.gfjc.Pojo.User;
import com.example.gfjc.Pojo.WorkSheet;
import com.example.gfjc.Service.PictureService;
import com.example.gfjc.Service.UserService;
import com.example.gfjc.Service.WorkSheetService;
import com.example.gfjc.common.BaseContext;
import com.example.gfjc.common.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @title WorkSheetController
 * @Author: ZKY
 * @CreateTime: 2024-04-02  11:00
 * @Description: TODO
 */

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/WorkSheet")
public class WorkSheetController {
    @Autowired
    private WorkSheetService workSheetService;

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    @Value("${GFJC.repairedHttpPath}")
    private String repairedHttpPath;

    @Value("${GFJC.repairedBasePath}")
    private String repairedBasePath;

    @ApiOperation("巡检人员生成工单")
    @PostMapping("/generate/{picId}")
    public Result<String> generate(@PathVariable String picId, String userId, String address, Double latitude, Double longitude){
        Picture pic = pictureService.getById(picId);
        User inspector = userService.getById(userId);

        String inspectorInfo = "巡检人:" + inspector.getNickName() + "; 电话:" + inspector.getPhone();

        WorkSheet sheet = new WorkSheet();
        sheet.setPicId(pic.getId());
        sheet.setInspectId(inspector.getId());
        sheet.setInstrumentType(pic.getInstrumentType());
        sheet.setUploadType(pic.getUploadType());
        sheet.setRiskLevel(pic.getRiskLevel());
        sheet.setDefectType(pic.getDefectType());
        sheet.setInspectedInfo(inspectorInfo);
        sheet.setOriginalUrl(pic.getOriginalUrl());
        sheet.setAnalyzedUrl(pic.getAnalyzedUrl());
        sheet.setIfReview(0);
        sheet.setStatus(WorkSheetStatus.WORK_SHEET_STATUS1.getMessage());
        sheet.setDescription(pic.getDescription());
        sheet.setIfConfirm(0);

        sheet.setLatitude(latitude);
        sheet.setLongitude(longitude);
        sheet.setAddress(address);

        if (workSheetService.save(sheet))
            return Result.success("会审工单生成，待专家会审");
        else
            return Result.error("发生错误");
    }

    @ApiOperation("查询当前巡检人员的识别记录")
    @GetMapping("/historyInfo/{userId}")
    public Result<List<WorkSheet>> historyInfo(@PathVariable String userId){
        LambdaQueryWrapper<WorkSheet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userId), WorkSheet::getInspectId, userId);
        queryWrapper.orderByAsc(WorkSheet::getUpdateTime);
        List<WorkSheet> list = workSheetService.list(queryWrapper);

        return Result.success(list);
    }


    @ApiOperation("工单的确认操作")
    @PostMapping("/confirm")
    public Result<String> confirm(@RequestBody WorkSheet workSheet, String userId){
        User user = userService.getById(userId);
        String job = user.getJob();
        String userinfo = job+": "+user.getNickName()+"; 电话: "+user.getPhone();
        if (job.equals("会审专家")) {
            if (workSheet.getStatus().equals(WorkSheetStatus.WORK_SHEET_STATUS1.getMessage()) && workSheet.getIfConfirm() == 0) {
                workSheet.setIfConfirm(1);
                workSheetService.updateById(workSheet);
                return Result.success("确认成功");
            }
        }

        return Result.error("出现未知错误");
    }

    @ApiOperation("工单的更新操作")
    @PostMapping("/update")
    public Result<String> updateByExpert(@RequestBody WorkSheet workSheet, Long userId, String msg){
        User user = userService.getById(userId);
        String job = user.getJob();
        String userinfo = job+": "+user.getNickName()+"; 电话: "+user.getPhone();
        if (job.equals("会审专家")){
            if (workSheet.getStatus().equals(WorkSheetStatus.WORK_SHEET_STATUS1.getMessage())){
                workSheet.setExpertInfo(userinfo);
                workSheet.setIfReview(1);
                workSheet.setExpertId(user.getId());
                workSheet.setStatus(WorkSheetStatus.WORK_SHEET_STATUS2.getMessage());
                workSheetService.updateById(workSheet);
                return Result.success("专家会审完成，待维修人员接单");
            }else if(workSheet.getStatus().equals(WorkSheetStatus.WORK_SHEET_STATUS4.getMessage())
                    && String.valueOf(workSheet.getExpertId()).equals(String.valueOf(user.getId()))){
                workSheet.setStatus(msg);
                if(msg.equals(WorkSheetStatus.WORK_SHEET_STATUS6.getMessage())){
                    workSheet.setIfRepair(0);
                }
                workSheetService.updateById(workSheet);
                return Result.success("反馈成功");
            }
        }else if (job.equals("维修人员") && String.valueOf(workSheet.getWorkerId()) .equals(String.valueOf(user.getId()))){
            if (workSheet.getStatus().equals(WorkSheetStatus.WORK_SHEET_STATUS2.getMessage()) || workSheet.getStatus().equals(WorkSheetStatus.WORK_SHEET_STATUS6.getMessage())){
                workSheet.setWorkerInfo(userinfo);
                workSheet.setStatus(WorkSheetStatus.WORK_SHEET_STATUS3.getMessage());
                workSheetService.updateById(workSheet);
                return Result.success("维修人员成功接单，请按时完成维修");
            }else if (workSheet.getStatus().equals(WorkSheetStatus.WORK_SHEET_STATUS3.getMessage())){
               workSheet.setStatus(WorkSheetStatus.WORK_SHEET_STATUS4.getMessage());
               workSheet.setIfRepair(1);
               if (StringUtils.isNotEmpty(workSheet.getRepairedUrl())){
                   workSheet.setRepairedUrl(repairedHttpPath+workSheet.getRepairedUrl());
               }
                workSheetService.updateById(workSheet);
                return Result.success("上传成功，待专家复审");
            }
        }

        return Result.error("出现未知错误");
    }

    @ApiOperation("专家页面工单分页查询")
    @GetMapping("/expert/page")
    public Result<Page<WorkSheet>> pageExpert(Integer page, Integer pageSize, String status, String expertId){
        log.info("page = {}, pageSize = {}",page,pageSize);
        Page<WorkSheet> pageInfo = new Page<WorkSheet>(page,pageSize);
        LambdaQueryWrapper<WorkSheet> queryWrapper = new LambdaQueryWrapper<>();
        if (status.equals(WorkSheetStatus.WORK_SHEET_STATUS1.getMessage())){
            /*
            * 前端传的参数status：
            *   "待专家审核"：查询所有待审核的工单
            * */
            queryWrapper.eq(StringUtils.isNotEmpty(status),WorkSheet::getStatus, status);
        }else{
            /*
            * 查询与当前登录专家相关的工单
            * 前端传的参数status：
            *   "待维修人员接单"：查询当前登陆的专家派出的且尚未被接单的工单
            *   "维修人员已接单，待维修"：查询当前登陆专家派出的并且正在维修的工单
            *   "维修完成，待专家复审"：查询当前登陆专家派出的并且维修完成待复审的工单
            *   "专家复审通过，可销项"：查询当前登陆专家派出的并且复审通过的工单
            *   "专家复审未通过，返工"：查询当前登陆专家派出的并且复审未通过的工单
            * */
            log.info("id=============" + expertId);
            queryWrapper.eq(StringUtils.isNotEmpty(status),WorkSheet::getStatus, status);
            queryWrapper.eq(WorkSheet::getExpertId,expertId);

        }
        queryWrapper.orderByDesc(WorkSheet::getCreateTime);

        workSheetService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    @ApiOperation("维修人员页面工单分页查询")
    @GetMapping("/worker/page")
    public Result<Page<WorkSheet>> pageWorker(Integer page, Integer pageSize, String status, String workerId){
        log.info("page = {}, pageSize = {}",page,pageSize);
        Page<WorkSheet> pageInfo = new Page<WorkSheet>(page,pageSize);
        LambdaQueryWrapper<WorkSheet> queryWrapper = new LambdaQueryWrapper<>();

        /*
         * 查询与当前登录专家相关的工单
         * 前端传的参数status：
         *   "待维修人员接单"：查询当前登陆的维修人员被指派但尚未被接单的工单
         *   "维修人员已接单，待维修"：查询当前登陆维修人员接受的并且正在维修的工单
         *   "维修完成，待专家复审"：查询当前登陆维修人员维修完成待复审的工单
         *   "专家复审通过，可销项"：查询当前登陆维修人员复审通过的工单
         *   "专家复审未通过，返工"：查询当前登陆维修人员复审未通过的工单
         * */
        queryWrapper.eq(StringUtils.isNotEmpty(status),WorkSheet::getStatus, status);
        queryWrapper.eq(WorkSheet::getWorkerId,workerId);

        queryWrapper.orderByDesc(WorkSheet::getCreateTime);

        workSheetService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    @ApiOperation("工单删除")
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        if (!workSheetService.removeById(id)){
            return Result.error("出现错误");
        }
        return Result.success("删除成功");
    }
}

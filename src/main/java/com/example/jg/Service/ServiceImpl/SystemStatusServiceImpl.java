package com.example.jg.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jg.Enum.WorkSheetStatus;
import com.example.jg.Mapper.SystemStatusMapper;
import com.example.jg.Pojo.SystemStatus;
import com.example.jg.Pojo.User;
import com.example.jg.Pojo.WorkSheet;
import com.example.jg.Service.SystemStatusService;
import com.example.jg.Service.UserService;
import com.example.jg.Service.WorkSheetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title SystemStatusService
 * @Author: ZKY
 * @CreateTime: 2024-04-22  11:22
 * @Description: TODO
 */
@Service
@Slf4j
public class SystemStatusServiceImpl extends ServiceImpl<SystemStatusMapper,SystemStatus> implements SystemStatusService {
    @Autowired
    public UserService userService;

    @Autowired
    public WorkSheetService workSheetService;

    @Override
    public User findWorker() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getJob, "维修人员");
        List<User> list = userService.list(queryWrapper);
        int min = Integer.MAX_VALUE;
        User minUser = new User();
        for (User u : list){
            LambdaQueryWrapper<WorkSheet> wsQueryWrapper = new LambdaQueryWrapper<>();
            wsQueryWrapper.eq(WorkSheet::getWorkerId, u.getId())
                    .and(wrapper -> wrapper
                            .eq(WorkSheet::getStatus, WorkSheetStatus.WORK_SHEET_STATUS3.getMessage())
                            .or()
                            .eq(WorkSheet::getStatus,WorkSheetStatus.WORK_SHEET_STATUS2.getMessage()));

            int count = workSheetService.count(wsQueryWrapper);
            log.info(count+"");
            if (count == 0){
                min = count;
                minUser = u;
                break;
            }
            if (count < min){
                min = count;
                minUser = u;
            }
        }
        return minUser;
    }
}

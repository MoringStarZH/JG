package com.example.jg.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jg.Pojo.Feedback;
import com.example.jg.Pojo.User;
import com.example.jg.Service.FeedbackService;
import com.example.jg.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @title FeedbackController
 * @Author: ZKY
 * @CreateTime: 2024-05-07  19:25
 * @Description: TODO
 */
@RestController
@Slf4j
@RequestMapping("/feedback")
@CrossOrigin
public class FeedbackController {

    @Autowired
    private RedisTemplate<Object, User> redisTemplate;

    @Autowired
    private FeedbackService feedbackService;

    @ApiOperation("小程序用户进行反馈")
    @PostMapping("/save")
    public Result<String> save(HttpServletRequest request, String content, String phone){
        String token = request.getHeader("Authorization");
        log.info(token);
        User user = redisTemplate.opsForValue().get(token);
        if (user == null){
            return Result.error("该用户不存在或未登录");
        }
        Feedback feedback = new Feedback();
        feedback.setUserPhone(phone);
        feedback.setContent(content);
        feedback.setUserId(user.getId());
        feedback.setStatus("待处理");
        feedback.setSuggestion("暂无处理意见");
        if (!feedbackService.save(feedback)){
            return Result.error("反馈失败，出现未知错误");
        }
        return Result.success("反馈成功，工作人员将及时处理");
    }

    @ApiOperation("对反馈进行处理")
    @PostMapping("/solve")
    public Result<String> solve(@RequestBody Feedback feedback){
        feedback.setStatus("已处理");
        if (!feedbackService.updateById(feedback)){
            return Result.error("出现未知错误");
        }
        return Result.success("处理成功");
    }

    @ApiOperation("反馈信息分页查询展示")
    @GetMapping("/page")
    public Result<Page<Feedback>> page(Integer page, Integer pageSize, Long userid){
        log.info("page = {}, pageSize = {}",page,pageSize);
        Page<Feedback> pageInfo = new Page<Feedback>(page,pageSize);

        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(String.valueOf(userid)), Feedback::getUserId, userid);
        queryWrapper.orderByDesc(Feedback::getStatus);

        feedbackService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }
}



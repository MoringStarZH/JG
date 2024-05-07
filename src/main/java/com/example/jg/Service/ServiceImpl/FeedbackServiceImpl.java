package com.example.jg.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jg.Mapper.FeedbackMapper;
import com.example.jg.Mapper.KBaseMapper;
import com.example.jg.Pojo.Feedback;
import com.example.jg.Pojo.KBase;
import com.example.jg.Service.FeedbackService;
import com.example.jg.Service.KBaseService;
import org.springframework.stereotype.Service;

/**
 * @title FeedbackServiceImpl
 * @Author: ZKY
 * @CreateTime: 2024-05-07  19:24
 * @Description: TODO
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {
}

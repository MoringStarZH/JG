package com.example.jg.Aspect;

import com.example.jg.MyAnnotation.TakeCount;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class TakeCountAspect {
 
    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate redisTemplate;
 
    //用ThreadLocal记录当前线程访问接口的开始时间
    private ThreadLocal<Long> startTime = new ThreadLocal<>();
 
    //扫描所有添加了@TakeCount注解的方法
    @Before("@annotation(takeCount)")
    public void doBefore(TakeCount takeCount){
        //记录接口的开始时间
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求的内容

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(date);
//        String url = request.getRequestURL().toString();
        //如果缓存当中没有当前接口的key就进行存储，如果有的话就对应接口的访问数据自增加一
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(today, "num",takeCount.time(), TimeUnit.SECONDS);
        if(ifAbsent){
            redisTemplate.opsForValue().set(today+":count",1);
        }else{
            redisTemplate.opsForValue().increment(today+":count");
        }
    }
 
    //接口方法执行完成之后
    @After("@annotation(com.example.jg.MyAnnotation.TakeCount)")
    public void doAfter(JoinPoint joinPoint){
        //将当前的事件减去之前的事件
        log.info("{}访问时间为：{}ms",joinPoint.getSignature().getName(),(System.currentTimeMillis()-startTime.get()));
    }
 
}

package com.example.gfjc.Listener;

import com.example.gfjc.Pojo.VisitsNum;
import com.example.gfjc.Service.VisitsNumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    @Autowired
    private VisitsNumService visitsNumService;

    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate redisTemplate;
 
    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
 
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String date = message.toString();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(date,dateTimeFormatter);

        int num = (int) redisTemplate.opsForValue().get(message.toString()+":count");
        //获取对应的接口的访问次数
        log.info("系统在{}内被访问{}次",message,num);
        VisitsNum visitsNum = new VisitsNum();
        visitsNum.setCreateDate(date1);
        visitsNum.setNum(num);
        visitsNumService.save(visitsNum);
        //将对应接口的访问数据的key进行删除
        redisTemplate.delete(message.toString()+":count");
    }
}

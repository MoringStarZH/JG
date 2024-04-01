package com.example.gfjc.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @title MyMetaObjectHandler
 * @Author: ZKY
 * @CreateTime: 2024-03-21  14:06
 * @Description: TODO
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /*
     *插入操作时自动填充
     * */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
    }

    /*
     * 更新操作时自动填充
     * */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        log.info(metaObject.toString());
        metaObject.setValue("updateTime",LocalDateTime.now());
    }
}

package com.example.jg.Pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * @title Picture
 * @Author: ZKY
 * @CreateTime: 2024-03-21  18:01
 * @Description: TODO
 */
@Data
public class Picture {
    private String id;

    private String size;

//    private String status;

    private String userInfo;

    private String defectType;

    private String riskLevel;

    private String instrumentType;

    private String uploadType;

    private String originalUrl;

    private String analyzedUrl;

    private String repairedUrl;

    private String description;

    //插入时自动填充字段
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    //插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;
}

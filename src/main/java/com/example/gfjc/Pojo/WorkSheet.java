package com.example.gfjc.Pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @title WorkSheet
 * @Author: ZKY
 * @CreateTime: 2024-04-02  08:43
 * @Description: TODO
 */
@Data
public class WorkSheet {
    private Long id;

    private String picId;

    private Long inspectId;

    private Long expertId;

    private Long workerId;

    private String instrumentType;

    private String uploadType;

    private String riskLevel;

    private String description;

    private String advice;

    private String defectType;

    private String inspectedInfo;

    private String expertInfo;

    private String workerInfo;

    private String originalUrl;

    private String analyzedUrl;

    private String repairedUrl;

    private int ifReview;

    private int ifRepair;

    private int ifConfirm;

    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime expectedTime;

    //插入时自动填充字段
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    //插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;


    private String address;

    private Double Longitude;

    private Double Latitude;
}

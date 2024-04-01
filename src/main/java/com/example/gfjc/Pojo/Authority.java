package com.example.gfjc.Pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @title Limits
 * @Author: ZKY
 * @CreateTime: 2024-04-01  16:00
 * @Description: TODO
 */
@Data
public class Authority {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String job;

    private int user;

    private int picture;

    private int jro;

    private int wo;

    private int kbase;

    private int enterprise;
}

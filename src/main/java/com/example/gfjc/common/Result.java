package com.example.gfjc.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @title Result
 * @Author: ZKY
 * @CreateTime: 2024-03-21  14:06
 * @Description: TODO
 */
@Data
public class Result<T> {
    private Integer code;

    private String msg;

    private T data;

    private Map map = new HashMap();

    public static <T> Result<T> success(T object){
        Result<T> r = new Result<>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> Result<T> error(String object){
        Result<T> r = new Result<>();
        r.msg = object;
        r.code = 0;
        return r;
    }

    public Result<T> add(String key, String value){
        this.map.put(key,value);
        return this;
    }
}
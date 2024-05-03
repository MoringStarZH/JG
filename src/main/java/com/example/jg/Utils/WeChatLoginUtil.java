package com.example.jg.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

/**
 * @title WeChatLoginUtil
 * @Author: ZKY
 * @CreateTime: 2024-05-03  17:56
 * @Description: TODO
 */
public class WeChatLoginUtil {

    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    public static JSONObject getResponseJson(String code){
        Map<String,String> claims = new HashMap<>();
        claims.put("appid","wxf2c8e8951dc63c71");
        claims.put("secret","bc3372ffde7be10ffcc46aea2fa3f0b0");
        claims.put("js_code",code);
        claims.put("grant_type", "authorization_code");
        String s = HttpClientUtil.doGet(WX_LOGIN, claims);
        return JSON.parseObject(s);
    }
}

package com.example.jg.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @title WeChatLoginUtil
 * @Author: ZKY
 * @CreateTime: 2024-05-03  17:56
 * @Description: TODO
 */
@Slf4j
public class WeChatLoginUtil {

    public static JSONObject getResponseJson(String code){
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String,String> claims = new HashMap<>();
        claims.put("appid","wxf2c8e8951dc63c71");
        claims.put("secret","bc3372ffde7be10ffcc46aea2fa3f0b0");
        claims.put("js_code",code);
        claims.put("grant_type", "authorization_code");
        String s = HttpClientUtil.doGet(requestUrl, claims);
        return JSON.parseObject(s);
    }

//    public static JSONObject getAccessToken(){
//        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
//        Map<String,String> requestUrlParam = new LinkedHashMap<>();
//        requestUrlParam.put("appid","wx19ebb2675c40e586");
//        requestUrlParam.put("secret","01494e3f4d128eca6a7db1975916ed20");
//        requestUrlParam.put("grant_type", "client_credential");
//        return JSON.parseObject(HttpClientUtil.doGet(requestUrl, requestUrlParam));
//    }
//
//    public static JSONObject getPhoneNum(String access_token, String code){
//        String requestUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token="+access_token;
//        log.info(requestUrl);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code", code);
//        log.info(jsonObject.toString());
//        return JSON.parseObject(HttpClientUtil.doPostJson(requestUrl, jsonObject.toString()));
//    }
}

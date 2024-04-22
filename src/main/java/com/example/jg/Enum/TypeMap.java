package com.example.jg.Enum;

import java.util.HashMap;

/**
 * @title TypeMap
 * @Author: ZKY
 * @CreateTime: 2024-04-09  10:05
 * @Description: TODO
 */
public class TypeMap {
    public static HashMap<String, String> map = new HashMap<>();
    static{
        map.put("井圈问题","三级风险");
        map.put("井盖缺失","三级风险");
        map.put("井盖未盖（翘起）","二级风险");
        map.put("井盖破损","二级风险");
        map.put("井盖完好","一级风险");
    }

    public static HashMap<String, Integer> typeNumMap = new HashMap<>();
    /*JG*/
    static{
        typeNumMap.put("井盖完好",0);
        typeNumMap.put("井盖破损",0);
        typeNumMap.put("井盖缺失",0);
        typeNumMap.put("井盖未盖（翘起）",0);
        typeNumMap.put("井圈问题",0);
    }

    public static HashMap<String, Integer> riskNumMap = new HashMap<>();
    /*GFJC*/
    static{
        riskNumMap.put("二级风险",0);
        riskNumMap.put("三级风险",0);
        riskNumMap.put("一级风险",0);
    }




}



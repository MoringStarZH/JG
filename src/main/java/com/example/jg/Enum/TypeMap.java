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
        map.put("裂痕","三级风险");
        map.put("物理损坏","三级风险");
        map.put("电气损坏","三级风险");
        map.put("脏污","二级风险");
        map.put("积雪覆盖","二级风险");
        map.put("清洁","一级风险");
    }

    public static HashMap<String, Integer> typeNumMap = new HashMap<>();
    /*GFJC*/
    static{
        typeNumMap.put("裂痕",0);
        typeNumMap.put("物理损坏",0);
        typeNumMap.put("电气损坏",0);
        typeNumMap.put("脏污",0);
        typeNumMap.put("积雪覆盖",0);
        typeNumMap.put("清洁",0);
    }

    /*JG*/
//    static{
//        typeNumMap.put("井盖完好",0);
//        typeNumMap.put("井盖破损",0);
//        typeNumMap.put("井盖缺失",0);
//        typeNumMap.put("井盖未盖（翘起）",0);
//        typeNumMap.put("井圈问题",0);
//    }

    public static HashMap<String, Integer> riskNumMap = new HashMap<>();
    /*GFJC*/
    static{
        riskNumMap.put("二级风险",0);
        riskNumMap.put("三级风险",0);
        riskNumMap.put("一级风险",0);
    }




}



package com.example.gfjc.Enum;

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
}



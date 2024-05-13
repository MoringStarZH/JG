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
        map.put("井圈问题","二级风险");
        map.put("丢失","一级风险");
        map.put("未覆盖","一级风险");
        map.put("破损","二级风险");
        map.put("完好","三级风险");
    }

    public static HashMap<String, Integer> typeNumMap = new HashMap<>();
    /*JG*/
    static{
        typeNumMap.put("完好",0);
        typeNumMap.put("破损",0);
        typeNumMap.put("丢失",0);
        typeNumMap.put("未覆盖",0);
        typeNumMap.put("井圈问题",0);
    }

    public static HashMap<String, Integer> riskNumMap = new HashMap<>();
    /*GFJC*/
    static{
        riskNumMap.put("二级风险",0);
        riskNumMap.put("三级风险",0);
        riskNumMap.put("一级风险",0);
    }

    public static HashMap<String, Integer> workSheetMap = new HashMap<>();
    /*GFJC*/
    static{
        workSheetMap.put("待专家审核",0);
        workSheetMap.put("待维修人员接单",0);
        workSheetMap.put("维修人员已接单，待维修",0);
        workSheetMap.put("维修完成，待专家复审",0);
        workSheetMap.put("专家复审通过，可销项",0);
        workSheetMap.put("专家复审未通过，返工",0);
    }



}



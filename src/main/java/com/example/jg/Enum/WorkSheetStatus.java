package com.example.jg.Enum;

/**
 * @title WorkSheetStatus
 * @Author: ZKY
 * @CreateTime: 2024-04-02  11:52
 * @Description: TODO
 */
public enum WorkSheetStatus implements Enum{
    WORK_SHEET_STATUS1("待专家审核"),
    WORK_SHEET_STATUS2("待维修人员接单"),
    WORK_SHEET_STATUS3("维修人员已接单，待维修"),
    WORK_SHEET_STATUS4("维修完成，待专家复审"),
    WORK_SHEET_STATUS5("专家复审通过，可销项"),
    WORK_SHEET_STATUS6("专家复审未通过，返工");


    private final String message;

    WorkSheetStatus(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package com.xunxiaozdh.mydata;

import java.io.Serializable;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/30 0030 10:06
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class CheckBom implements Serializable{

    Integer equipment_ChkItem_ID;// id 自动增长

    String worksection;// 工段

    String equipment_code;// 设备编号

    String equipment_name;// 设备名称

    String workCenter_Code;

    String Check_Type;// 周检或其他

    Integer check_ItemID;// 检查项编号

    String check_Item;// 检查项

    String check_Demand;// 检查标准

    String check_Importance;// 重要程度

    String check_Method;// 检查指导

    public Integer getEquipment_ChkItem_ID() {

        return equipment_ChkItem_ID;
    }

    public void setEquipment_ChkItem_ID(Integer equipment_ChkItem_ID) {
        this.equipment_ChkItem_ID = equipment_ChkItem_ID;
    }

    public String getWorksection() {
        return worksection;
    }

    public void setWorksection(String worksection) {
        this.worksection = worksection;
    }

    public String getEquipment_code() {
        return equipment_code;
    }

    public void setEquipment_code(String equipment_code) {
        this.equipment_code = equipment_code;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public String getWorkCenter_Code() {
        return workCenter_Code;
    }

    public void setWorkCenter_Code(String workCenter_Code) {
        this.workCenter_Code = workCenter_Code;
    }

    public Integer getCheck_ItemID() {
        return check_ItemID;
    }

    public void setCheck_ItemID(Integer check_ItemID) {
        this.check_ItemID = check_ItemID;
    }

    public String getCheck_Item() {
        return check_Item;
    }

    public void setCheck_Item(String check_Item) {
        this.check_Item = check_Item;
    }

    public String getCheck_Demand() {
        return check_Demand;
    }

    public void setCheck_Demand(String check_Demand) {
        this.check_Demand = check_Demand;
    }

    public String getCheck_Importance() {
        return check_Importance;
    }

    public void setCheck_Importance(String check_Importance) {
        this.check_Importance = check_Importance;
    }

    public String getCheck_Method() {
        return check_Method;
    }

    public void setCheck_Method(String check_Method) {
        this.check_Method = check_Method;
    }

    public String getCheck_Type() {
        return Check_Type;
    }

    public void setCheck_Type(String check_Type) {
        Check_Type = check_Type;
    }
}

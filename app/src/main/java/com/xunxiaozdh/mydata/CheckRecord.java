package com.xunxiaozdh.mydata;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/14.
 */

public class CheckRecord implements Serializable {

    Integer ID; // 序号，自动增长
    String check_Date;//点检时间,yy/MM/dd
    String check_Time;// 点检时间, HH:mm:ss
    Integer check_Year;// 年
    Integer check_Month; // 月
    String worksection;// 工段
    String workCenter_Code;//
    String equipment_name;// 设备名称
    String equipment_code; // 设备编号
    String check_Type;// 检查类型
    String check_Item;// 检查项
    Integer check_ItemID;// 检查项id
    String check_Demand;// 检查标准
    String check_Importance;// 重要程度
    String check_Method;// 指导说明
    String check_Result;// 检查结果
    String check_Person;// 操作者
    String last_Check_Date;// yy/MM/dd HH:mm:ss

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCheck_Date() {
        return check_Date;
    }

    public void setCheck_Date(String check_Date) {
        this.check_Date = check_Date;
    }

    public String getCheck_Time() {
        return check_Time;
    }

    public void setCheck_Time(String check_Time) {
        this.check_Time = check_Time;
    }

    public Integer getCheck_Year() {
        return check_Year;
    }

    public void setCheck_Year(Integer check_Year) {
        this.check_Year = check_Year;
    }

    public Integer getCheck_Month() {
        return check_Month;
    }

    public void setCheck_Month(Integer chek_Month) {
        this.check_Month = chek_Month;
    }

    public String getWorksection() {
        return worksection;
    }

    public void setWorksection(String worksection) {
        this.worksection = worksection;
    }

    public String getWorkCenter_Code() {
        return workCenter_Code;
    }

    public void setWorkCenter_Code(String workCenter_Code) {
        this.workCenter_Code = workCenter_Code;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public String getEquipment_code() {
        return equipment_code;
    }

    public void setEquipment_code(String equipment_code) {
        this.equipment_code = equipment_code;
    }

    public String getCheck_Type() {
        return check_Type;
    }

    public void setCheck_Type(String ckeck_Type) {
        this.check_Type = ckeck_Type;
    }

    public String getCheck_Item() {
        return check_Item;
    }

    public void setCheck_Item(String check_Item) {
        this.check_Item = check_Item;
    }

    public Integer getCheck_ItemID() {
        return check_ItemID;
    }

    public void setCheck_ItemID(Integer check_ItemID) {
        this.check_ItemID = check_ItemID;
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

    public String getCheck_Result() {
        return check_Result;
    }

    public void setCheck_Result(String check_Result) {
        this.check_Result = check_Result;
    }

    public String getCheck_Person() {
        return check_Person;
    }

    public void setCheck_Person(String check_Person) {
        this.check_Person = check_Person;
    }

    public String getLast_Check_Date() {
        return last_Check_Date;
    }

    public void setLast_Check_Date(String last_Check_Date) {
        this.last_Check_Date = last_Check_Date;
    }
}

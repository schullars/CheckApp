package com.xunxiaozdh.mydata;

import java.io.Serializable;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/9/15 08:09
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class StaffPermission implements Serializable {

    Integer ID;// 自动增长
    Integer jobNumber;
    Integer vCardNo;
    String psn_Name;
    String depName;
    String dutyclass;
    String jobcode;
    //String tempDutyclass;
    Integer screen30;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(Integer jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Integer getvCardNo() {
        return vCardNo;
    }

    public void setvCardNo(Integer vCardNo) {
        this.vCardNo = vCardNo;
    }

    public String getPsn_Name() {
        return psn_Name;
    }

    public void setPsn_Name(String psn_Name) {
        this.psn_Name = psn_Name;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDutyclass() {
        return dutyclass;
    }

    public void setDutyclass(String dutyclass) {
        this.dutyclass = dutyclass;
    }

    public String getJobcode() {
        return jobcode;
    }

    public void setJobcode(String jobcode) {
        this.jobcode = jobcode;
    }

    public Integer getScreen30() {
        return screen30;
    }

    public void setScreen30(Integer screen30) {
        this.screen30 = screen30;
    }
}

package xupt.se.ttms.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Sale {
    private long sId;//销售记录id
    private  int empId; //员工id
    private String dTime; //销售日期
    private float pMent; //付款金额
    private float sChange; //找零
    private int sType; //销售单类型:1:销售单,-1退款单
    private int status; //0:代付款,1:已付款

    public long getsId() {
        return sId;
    }

    public void setsId(long sId) {
        this.sId = sId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getdTime() {
        return dTime;
    }

    public void setdTime(String dTime) {
        this.dTime = dTime;
    }

    public float getpMent() {
        return pMent;
    }

    public void setpMent(float pMent) {
        this.pMent = pMent;
    }

    public float getsChange() {
        return sChange;
    }

    public void setsChange(float sChange) {
        this.sChange = sChange;
    }

    public int getsType() {
        return sType;
    }

    public void setsType(int sType) {
        this.sType = sType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

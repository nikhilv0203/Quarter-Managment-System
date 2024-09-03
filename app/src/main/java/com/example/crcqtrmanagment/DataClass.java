package com.example.crcqtrmanagment;

public class DataClass {

    private String QtrNo;
    private String EmployeName;
    private String Designation;
    private String neisno;
    private String unit;
    private String other;
    private String phoneNo;
    private String Remark;
    private String key;

    // Getter and Setter methods
    public String getKey() {
        return key;
    }

    public String getQtrNo() {
        return QtrNo;
    }

    public void setQtrNo(String qtrNo) {
        QtrNo = qtrNo;
    }

    public String getEmployeName() {
        return EmployeName;
    }

    public void setEmployeName(String employeName) {
        EmployeName = employeName;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getNeisno() {
        return neisno;
    }

    public void setNeisno(String neisno) {
        this.neisno = neisno;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    // Constructor
    public DataClass(String qtrNo, String employeName, String designation, String neisno, String unit, String other, String phoneNo, String remark) {
        this.QtrNo = qtrNo;
        this.EmployeName = employeName;
        this.Designation = designation;
        this.neisno = neisno;
        this.unit = unit;
        this.other = other;
        this.phoneNo = phoneNo;
        this.Remark = remark;
    }
    public DataClass() {
        // Default constructor required for Firebase
    }
}

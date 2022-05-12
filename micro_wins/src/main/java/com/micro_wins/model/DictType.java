package com.micro_wins.model;

import javax.persistence.*;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:26 AM
 */

@Entity
@Table(name="mw_dict_type")
public class DictType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dt_typeno", updatable = false, nullable = false)
    private int dtTypeNo;
    private String dtTypeName;
    private String dtEngName;
    private String dtTypeValue;

    public int getDtTypeNo() {
        return dtTypeNo;
    }

    public void setDtTypeNo(int dtTypeNo) {
        this.dtTypeNo = dtTypeNo;
    }

    public String getDtTypeName() {
        return dtTypeName;
    }

    public void setDtTypeName(String dtTypeName) {
        this.dtTypeName = dtTypeName;
    }

    public String getDtEngName() {
        return dtEngName;
    }

    public void setDtEngName(String dtEngName) {
        this.dtEngName = dtEngName;
    }

    public String getDtTypeValue() {
        return dtTypeValue;
    }

    public void setDtTypeValue(String dtTypeValue) {
        this.dtTypeValue = dtTypeValue;
    }

    @Override
    public String toString() {
        return "DictType{" +
                "dtTypeNo=" + dtTypeNo +
                ", dtTypeName='" + dtTypeName + '\'' +
                ", dtEngName='" + dtEngName + '\'' +
                ", dtTypeValue='" + dtTypeValue + '\'' +
                '}';
    }
}

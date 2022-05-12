package com.micro_wins.model;

import javax.persistence.*;

/**
 * @author Littl
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:30 AM
 */

@Entity
@Table(name="mw_dictionary")
public class Dict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dict_id", updatable = false, nullable = false)
    private int dictId;
    private int dictTypeNo;
    private String dictName;
    private int dictValue;

    public int getDictId() {
        return dictId;
    }

    public void setDictId(int dictId) {
        this.dictId = dictId;
    }

    public int getDictTypeNo() {
        return dictTypeNo;
    }

    public void setDictTypeNo(int dictTypeNo) {
        this.dictTypeNo = dictTypeNo;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public int getDictValue() {
        return dictValue;
    }

    public void setDictValue(int dictValue) {
        this.dictValue = dictValue;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "dictId=" + dictId +
                ", dictTypeNo=" + dictTypeNo +
                ", dictName='" + dictName + '\'' +
                ", dictValue=" + dictValue +
                '}';
    }
}

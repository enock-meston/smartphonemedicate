package com.nigoote.smartphonemedicate;

import java.io.Serializable;

public class DataModel implements Serializable {
    private String names,phoneNumber,disease,code1;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public DataModel(String names, String phoneNumber, String disease, String code1) {
        this.names = names;
        this.phoneNumber = phoneNumber;
        this.disease = disease;
        this.code1 = code1;
    }
}
package com.car.front.bean;

import java.io.Serializable;

/**
 * @author: zt
 * @date: 2020/6/10
 * @name:DemoBean
 */
public class DemoBean implements Serializable {
    private String cardNum;
    private String name;
    private String model;
    private String faults;

    public DemoBean(String cardNum, String name, String model, String faults) {
        this.cardNum=cardNum;
        this.name=name;
        this.model=model;
        this.faults=faults;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFaults() {
        return faults;
    }

    public void setFaults(String faults) {
        this.faults = faults;
    }


}

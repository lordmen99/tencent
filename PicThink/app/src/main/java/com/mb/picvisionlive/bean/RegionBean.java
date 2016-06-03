package com.mb.picvisionlive.bean;

/**
 * Created by wenm on 2016/6/3.
 */
public class RegionBean {
    private String name="";
    private int number;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

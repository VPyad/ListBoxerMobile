package com.example.vpyad.myapplication3.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by vpyad on 03-Jan-18.
 */

public class ListConfig implements Serializable {

    public ListConfig(){}

    public ListConfig(ListConfig listConfig) {
        this.name = listConfig.getName();
        this.mode = listConfig.getMode();
        this.sort = listConfig.getSort();
        this.list = listConfig.getList();
    }

    private String name = "DefaultName";
    private int mode = 2; // 0 - numeric, 1 - alphabetic, 2 - mixed
    private int sort = 0; // 0 - no sorting, 1 - acs, 2 - desc
    private ArrayList<String> list;

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public int getSort() {
        return sort;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }
}

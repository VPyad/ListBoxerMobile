package com.example.vpyad.myapplication3.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by vpyad on 03-Jan-18.
 */

public class ListConfig implements Serializable
{

    private String name = "DefaultName";
    private int mode = 3; // 1 - numeric, 2 - alphabetic, 3 - mixed
    private int sort = 1; // 1 - no sorting, 2 - acs, 3 - desc
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

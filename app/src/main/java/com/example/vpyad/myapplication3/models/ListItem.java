package com.example.vpyad.myapplication3.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by vpyad on 08-Jan-18.
 */

public class ListItem implements Serializable {

    public ListItem() {
    }

    public ListItem(String item, int mode) {
        this.mode = mode;
        this.item = item;
    }

    public ListItem(ListItem listItem) {
        this.mode = listItem.getMode();
        this.item = listItem.getItem();
    }

    private int mode = ListConfig.MODE_MIXED;
    private String item = "";

    public int getMode() {
        return mode;
    }

    public String getItem() {
        return item;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public static Comparator<ListItem> getAscComparator(){
        return new Comparator<ListItem>() {
            @Override
            public int compare(ListItem listItem, ListItem t1) {
                return listItem.getItem().compareTo(t1.getItem());
            }
        };
    }

    public static Comparator<ListItem> getDescComparator(){
        return new Comparator<ListItem>() {
            @Override
            public int compare(ListItem listItem, ListItem t1) {
                return t1.getItem().compareTo(listItem.getItem());
            }
        };
    }
}

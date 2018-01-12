package com.example.vpyad.myapplication3.models;

import android.support.annotation.NonNull;

import com.example.vpyad.myapplication3.helpers.StringValidatorHelper;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by vpyad on 08-Jan-18.
 */

public class ListItem implements Serializable {

    public ListItem() {
    }

    public ListItem(String item) {
        this.item = item;

        if (StringValidatorHelper.allDigits(item)) {
            this.mode = ListConfig.MODE_NUMERIC;
        } else if (StringValidatorHelper.allLetters(item)) {
            this.mode = ListConfig.MODE_ALPHABETIC;
        } else {
            this.mode = ListConfig.MODE_MIXED;
        }
    }

    public ListItem(ListItem listItem) {
        this.mode = listItem.getMode();
        this.item = listItem.getItem();
    }

    private int mode;
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

    public static Comparator<ListItem> getAscComparator() {
        return new Comparator<ListItem>() {
            @Override
            public int compare(ListItem listItem, ListItem t1) {
                return listItem.getItem().compareTo(t1.getItem());
            }
        };
    }

    public static Comparator<ListItem> getDescComparator() {
        return new Comparator<ListItem>() {
            @Override
            public int compare(ListItem listItem, ListItem t1) {
                return t1.getItem().compareTo(listItem.getItem());
            }
        };
    }

    public static Comparator<ListItem> itemComparator
            = new Comparator<ListItem>() {

        public int compare(ListItem item1, ListItem item2) {

            String itemStr1 = item1.getItem().toUpperCase();
            String itemStr2 = item2.getItem().toUpperCase();

            //ascending order
            return itemStr1.compareTo(itemStr2);
        }

    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ListItem))
            return false;

        ListItem item = (ListItem) obj;

        return this.item.equals(item.item) && this.mode == item.mode;
    }
}

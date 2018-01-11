package com.example.vpyad.myapplication3.models;

import com.example.vpyad.myapplication3.helpers.StringValidatorHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vpyad on 03-Jan-18.
 */

public class ListConfig implements Serializable {

    public static final int MODE_NUMERIC = 0;
    public static final int MODE_ALPHABETIC = 1;
    public static final int MODE_MIXED = 2;

    public static final int SORT_NO_SORT = 0;
    public static final int SORT_ASC = 1;
    public static final int SORT_DESC = 2;

    private String name;
    private int mode;
    private int sort;
    private List<ListItem> list;
    private List<ListItem> defaultList;

    public ListConfig() {
        name = "Новый_список";
        mode = MODE_MIXED;
        sort = SORT_NO_SORT;
        list = new ArrayList<>();
        defaultList = new ArrayList<>();
    }

    public ListConfig(ListConfig listConfig) {
        this.name = listConfig.getName();
        this.mode = listConfig.getMode();
        this.sort = listConfig.getSort();
        this.list = listConfig.getList();
        defaultList = new ArrayList<>();

        defaultList.addAll(list);
    }

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

    public void setList(List<ListItem> list) {
        this.list = list;
        defaultList.addAll(list);
    }

    public List<ListItem> getList() {
        filterList();
        sortList();
        return list;
    }

    public boolean addToList(ListItem item) {
        if(isInputLegal(item.getItem())) {
            list.add(item);
            defaultList.add(item);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeItem(ListItem item) {
        return list.remove(item) && defaultList.remove(item);
    }

    public void filterList(int modeType) {
        if (modeType == MODE_ALPHABETIC || modeType == MODE_NUMERIC) {
            List<ListItem> filteredList = new ArrayList<ListItem>();
            for (ListItem item : defaultList) {
                if (item.getMode() == modeType) {
                    filteredList.add(item);
                }
            }
            list = filteredList;
        } else { /*if (modeType == MODE_MIXED && sort == ListConfig.SORT_NO_SORT) {*/
            list.clear();
            list.addAll(defaultList);
        }
        //}
    }

    public void filterList() {
        filterList(mode);
    }

    public void sortList(int sortType) {
        if (mode == ListConfig.MODE_MIXED && sort == ListConfig.SORT_NO_SORT) {
            list.clear();
            list.addAll(defaultList);
        } else {
            switch (sortType) {
                case SORT_ASC:
                    Collections.sort(list, ListItem.itemComparator);
                    break;
                case SORT_DESC:
                    Collections.sort(list, ListItem.itemComparator);
                    List<ListItem> reversed = new ArrayList();
                    for (int i = list.size() - 1; i >= 0; i--) {
                        ListItem t = list.get(i);
                        reversed.add(t);
                    }
                    list.clear();
                    list.addAll(reversed);
                    break;
            }
        }
    }

    public void sortList() {
        sortList(sort);
    }

    public int allItemsCount() {
        return defaultList.size();
    }

    public int filteredItemsCount() {
        return list.size();
    }

    public void removeAll() {
        defaultList.clear();
        list.clear();
    }

    public boolean isInputLegal(String s) {
        switch (mode) {
            case ListConfig.MODE_ALPHABETIC:
                return StringValidatorHelper.allLetters(s);
            case ListConfig.MODE_MIXED:
                return StringValidatorHelper.allLettersAndDigits(s);
            case ListConfig.MODE_NUMERIC:
                return StringValidatorHelper.allDigits(s);
            default:
                return false;
        }
    }
}

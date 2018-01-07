package com.example.vpyad.myapplication3.providers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vpyad on 07-Jan-18.
 */

public class LocalStorageProvider {

    public final static String WRITE_PERMISSION_KEY = "write-permission-key";
    public final static String CURRENT_LIST_PATH = "current-list-path";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public LocalStorageProvider(Activity activity) {
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean putInt(String key, int value) {
        editor.putInt(key, value);
        return editor.commit();
    }

    public boolean putString(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public boolean putBool(String key, Boolean value) {
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, Integer.MIN_VALUE);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public boolean getBool(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}

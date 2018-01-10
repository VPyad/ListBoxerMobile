package com.example.vpyad.myapplication3.providers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.vpyad.myapplication3.MainActivity;
import com.example.vpyad.myapplication3.helpers.StorageHelper;
import com.example.vpyad.myapplication3.models.ListConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by vpyad on 03-Jan-18.
 */

public class ListConfigProvider {

    final static String fileExt = ".lbm";

    @Nullable
    public static ListConfig getListConfigFromDir(String path) {
        /*if (path == "") {
            return null;
        } else {
            ListConfig config = loadSerializedConfig(path);
            return config;
        }*/
        ListConfig config = loadSerializedConfig(path);
        return config;
    }

    public static boolean setListConfigToDir(ListConfig config, String path, Activity activity) {
        path += "/" + config.getName();
        // TODO check if file with this name is existing
        /*File f = new File(path + fileExt);
        if (!f.exists())
            path += (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new Date()));*/
        return saveObject(config, path, activity);
    }

    private static boolean saveObject(ListConfig config, String path, Activity activity) {
        boolean success = true;
        path += fileExt;

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path)));
            objectOutputStream.writeObject(config);
            objectOutputStream.flush();
            objectOutputStream.close();

            LocalStorageProvider localStorageProvider = new LocalStorageProvider(activity);
            localStorageProvider.putString(LocalStorageProvider.CURRENT_LIST_PATH, path);
        } catch (Exception ex) {
            Log.v("Serialization Error:", ex.getMessage());
            ex.printStackTrace();
            success = false;
        }

        return success;
    }

    @Nullable
    private static ListConfig loadSerializedConfig(String path) {

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path)));
            return (ListConfig) objectInputStream.readObject();
        } catch (Exception ex) {
            Log.v("Deserialization Error:", ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }

    public static ListConfig getListConfig(String path, Activity activity) {
        if (path == "") {
            ListConfig listConfig = new ListConfig();

            setListConfigToDir(listConfig, StorageHelper.getPathToSave(), activity);
            return listConfig;
        } else {
            return getListConfigFromDir(path);
        }
    }
}

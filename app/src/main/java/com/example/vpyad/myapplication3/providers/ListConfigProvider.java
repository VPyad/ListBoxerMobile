package com.example.vpyad.myapplication3.providers;

import android.content.Context;
import android.util.Log;

import com.example.vpyad.myapplication3.models.ListConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by vpyad on 03-Jan-18.
 */

public class ListConfigProvider {

    final static String fileExt = ".lbm";
    final static String fileName = "tempList";

    public static ListConfig getListConfigFromCache(String path) {
        path += fileName + fileExt;
        ListConfig config = loadSerializedConfig(path);
        return config;
    }

    public static boolean setListConfigToCache(ListConfig config, String path) {
        path += fileName;
        return saveObject(config, path);
    }

    public static ListConfig getListConfigFromDir(String path) {
        ListConfig config = loadSerializedConfig(path);
        return config;
    }

    public static boolean setListConfigToDir(ListConfig config, String path) {
        path += "/" + config.getName();
        return saveObject(config, path);
    }

    private static boolean saveObject(ListConfig config, String path) {
        boolean success = true;
        path += fileExt;

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path)));
            objectOutputStream.writeObject(config);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception ex) {
            Log.v("Serialization Error:", ex.getMessage());
            ex.printStackTrace();
            success = false;
        }

        return success;
    }

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
}

package com.example.vpyad.myapplication3.helpers;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by vpyad on 04-Jan-18.
 */

public class StorageHelper {

    private static boolean externalStorageReadable, externalStorageWritable;

    public static boolean isExternalStorageReadable() {
        checkStorage();
        return externalStorageReadable;
    }

    public static boolean isExternalStorageWritable() {
        checkStorage();
        return externalStorageWritable;
    }

    public static boolean isExternalStorageReadableAndWritable() {
        checkStorage();
        return externalStorageReadable && externalStorageWritable;
    }

    private static void checkStorage() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            externalStorageReadable = externalStorageWritable = true;
        } else if (state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            externalStorageReadable = true;
            externalStorageWritable = false;
        } else {
            externalStorageReadable = externalStorageWritable = false;
        }
    }

    public static String getPathToSave() {
        if (isExternalStorageReadableAndWritable()) {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/ListBoxerMobile");
            dir.mkdirs();

            return dir.toString();
        } else {
            File storage = Environment.getDataDirectory();
            File dir = new File(storage.getAbsolutePath() + "/ListBoxerMobile");
            dir.mkdirs();

            return dir.toString();
        }
    }

    public static String getChacheDirPath(Context context) {
        return context.getCacheDir().toString();
    }
}

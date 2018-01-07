package com.example.vpyad.myapplication3.helpers;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import com.example.vpyad.myapplication3.MainActivity;

/**
 * Created by vpyad on 07-Jan-18.
 */

public class RequestPermissionHelper {
    public final static int WRITE_EXTERNAL_STORAGE_CODE = 101;

    public static void requestWriteExternalStoragePermission(Activity activity){
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                WRITE_EXTERNAL_STORAGE_CODE);
    }
}

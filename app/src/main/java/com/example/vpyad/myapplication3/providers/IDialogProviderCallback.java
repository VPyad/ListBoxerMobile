package com.example.vpyad.myapplication3.providers;

import com.example.vpyad.myapplication3.models.ListConfig;

/**
 * Created by vpyad on 07-Jan-18.
 */

public interface IDialogProviderCallback {
    public void onListConfigCallback(ListConfig res, Integer code);

    void onResultCodeCallback(int actionCode, int payloadCode);

    public void onYesNoCallback(Integer res, boolean commitAction);

    public void onFileChooserCallback(String path, Integer code);
}
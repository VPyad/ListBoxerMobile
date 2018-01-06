package com.example.vpyad.myapplication3.providers;

import com.example.vpyad.myapplication3.models.ListConfig;

/**
 * Created by vpyad on 07-Jan-18.
 */

public interface IDialogProviderCallback {
    public void onListConfigCallback(ListConfig res);

    public void onYesNoCallback(Integer res);
}
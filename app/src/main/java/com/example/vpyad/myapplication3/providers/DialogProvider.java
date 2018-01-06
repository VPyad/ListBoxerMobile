package com.example.vpyad.myapplication3.providers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vpyad.myapplication3.R;
import com.example.vpyad.myapplication3.models.ListConfig;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by vpyad on 06-Jan-18.
 */

public class DialogProvider {

    private Context context;

    public DialogProvider(Context context) {
        this.context = context;
    }

    public ListConfig showModeDialog(ListConfig inputListConfig) {
        ListConfig result = new ListConfig(inputListConfig);

        Collection<String> modes = Arrays.asList(context.getString(R.string.mode_numeric), context.getString(R.string.mode_alphabetic));
        Integer[] selectedIndxs;

        if (inputListConfig.getMode() == 2) {
            selectedIndxs = new Integer[]{0, 1};
        } else {
            selectedIndxs = new Integer[]{inputListConfig.getMode()};
        }

        Integer[] selected = (new MaterialDialog.Builder(this.context)
                .title(context.getString(R.string.mode_dialog_title))
                .items(modes)
                .autoDismiss(true)
                .positiveText(context.getString(R.string.dialog_positive_button))
                .negativeText(context.getString(R.string.dialog_negative_button))
                .alwaysCallMultiChoiceCallback()
                .itemsCallbackMultiChoice(selectedIndxs, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        boolean allowSelectionChange = which.length >= 1;
                        return allowSelectionChange;
                    }
                })
                .show()).getSelectedIndices();

        if (selected.length == 2) {
            result.setMode(2);
        } else {
            result.setMode(selected[0]);
        }

        return result;
    }
}

package com.example.vpyad.myapplication3.providers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

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

    public static final Integer OPEN_FILE_CODE = 1;
    public static final Integer SAVE_FILE_CODE = 2;
    public static final Integer DELETE_ITEM_CODE = 3;
    public static final Integer CLEAR_ALL_CODE = 4;

    private Context context;
    private final IDialogProviderCallback callbackOnListConfigDialog;

    public DialogProvider(Context context, final IDialogProviderCallback callbackOnListConfigDialog) {
        this.context = context;
        this.callbackOnListConfigDialog = callbackOnListConfigDialog;
    }

    public void showModeDialog(ListConfig listConfig) {
        Collection<String> modes = Arrays.asList(context.getString(R.string.mode_numeric), context.getString(R.string.mode_alphabetic));
        final Integer[] selectedIndxs;

        if (listConfig.getMode() == 2) {
            selectedIndxs = new Integer[]{0, 1};
        } else {
            selectedIndxs = new Integer[]{listConfig.getMode()};
        }

        final ListConfig innerListConfig = new ListConfig(listConfig);

        new MaterialDialog.Builder(this.context)
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
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Integer[] selected = dialog.getSelectedIndices();

                        if (!Arrays.equals(selected, selectedIndxs)) {
                            ListConfig result = new ListConfig(innerListConfig);

                            if (selected.length == 2) {
                                result.setMode(2);
                            } else {
                                result.setMode(selected[0]);
                            }

                            callbackOnListConfigDialog.onListConfigCallback(result);
                        }
                    }
                })
                .show();
    }

    public void showSortDialog(ListConfig listConfig) {
        Collection<String> sorts = Arrays.asList(context.getString(R.string.sort_no_sort), context.getString(R.string.sort_asc), context.getString(R.string.sort_desc));
        final ListConfig innerListConfig = new ListConfig(listConfig);
        new MaterialDialog.Builder(context)
                .title(context.getString(R.string.sort_dialog_title))
                .items(sorts)
                .autoDismiss(true)
                .positiveText(context.getString(R.string.dialog_positive_button))
                .negativeText(context.getString(R.string.dialog_negative_button))
                .itemsCallbackSingleChoice(innerListConfig.getSort(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (dialog.getSelectedIndex() != innerListConfig.getSort()) {
                            ListConfig result = new ListConfig(innerListConfig);
                            result.setSort(dialog.getSelectedIndex());

                            callbackOnListConfigDialog.onListConfigCallback(result);
                        }
                    }
                })
                .show();
    }

    public void showOpenFileDialog() {
        new MaterialDialog.Builder(context)
                .title(context.getString(R.string.open_file_dialog_title))
                .content(context.getString(R.string.open_file_dialog_message))
                .positiveText(context.getString(R.string.open_file_dialog_possitive_button))
                .negativeText(context.getString(R.string.open_file_dialog_negative_button))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        callbackOnListConfigDialog.onYesNoCallback(DialogProvider.OPEN_FILE_CODE);
                    }
                })
                .show();
    }

    public void showSaveFileDialog() {
        new MaterialDialog.Builder(context)
                .title(context.getString(R.string.save_dialog_title))
                .content(context.getString(R.string.save_dialog_message))
                .positiveText(context.getString(R.string.save_dialog_possitive_button))
                .negativeText(context.getString(R.string.save_dialog_negative_button))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        callbackOnListConfigDialog.onYesNoCallback(DialogProvider.SAVE_FILE_CODE);
                    }
                })
                .show();
    }

    public void showClearAllDialog() {
        new MaterialDialog.Builder(context)
                .title(context.getString(R.string.clear_all_dialog_title))
                .content(context.getString(R.string.clear_all_dialog_message))
                .positiveText(context.getString(R.string.dialog_positive_button))
                .negativeText(context.getString(R.string.dialog_negative_button))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        callbackOnListConfigDialog.onYesNoCallback(DialogProvider.CLEAR_ALL_CODE);
                    }
                })
                .show();
    }

    public void showDeleteItemDialog() {
        new MaterialDialog.Builder(context)
                .title(context.getString(R.string.remove_item_dialog_title))
                .content(context.getString(R.string.remove_item_dialog_message))
                .positiveText(context.getString(R.string.dialog_positive_button))
                .negativeText(context.getString(R.string.dialog_negative_button))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        callbackOnListConfigDialog.onYesNoCallback(DialogProvider.DELETE_ITEM_CODE);
                    }
                })
                .show();
    }
}

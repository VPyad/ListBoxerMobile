package com.example.vpyad.myapplication3.providers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vpyad.myapplication3.R;
import com.example.vpyad.myapplication3.helpers.StringValidatorHelper;
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
    public static final Integer APPLY_MODE_CODE = 5;
    public static final Integer APPLY_SORT_CODE = 6;
    public static final Integer CREATE_NEW_LIST_CODE = 7;

    private Context context;
    private final IDialogProviderCallback iDialogProviderCallback;

    public DialogProvider(Context context, final IDialogProviderCallback iDialogProviderCallback) {
        this.context = context;
        this.iDialogProviderCallback = iDialogProviderCallback;
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

                            iDialogProviderCallback.onListConfigCallback(result, APPLY_MODE_CODE);
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

                            iDialogProviderCallback.onListConfigCallback(result, APPLY_SORT_CODE);
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
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.OPEN_FILE_CODE);
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
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.SAVE_FILE_CODE);
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
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.CLEAR_ALL_CODE);
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
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.DELETE_ITEM_CODE);
                    }
                })
                .show();
    }

    public void showCreateNewListDialog() {
        final View positiveAction;
        final EditText nameInput;
        final CheckBox numericCheckbox, alphabeticCheckbox;

        MaterialDialog dialog =
                new MaterialDialog.Builder(context)
                        .title(R.string.new_list_dialog_title)
                        .customView(R.layout.dialog_create_new_list, true)
                        .positiveText(context.getString(R.string.dialog_positive_button))
                        .negativeText(context.getString(R.string.dialog_negative_button))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ListConfig listConfig = new ListConfig();

                                String name = ((EditText) dialog.getCustomView().findViewById(R.id.create_new_list_name_textedit)).getText().toString();
                                boolean isAlphabetic = ((CheckBox) dialog.getCustomView().findViewById(R.id.alphabetic_mode_ckeckbox)).isChecked();
                                boolean isNumeric = ((CheckBox) dialog.getCustomView().findViewById(R.id.numeric_mode_ckeckbox)).isChecked();

                                listConfig.setName(name);

                                if (isAlphabetic && isNumeric) {
                                    listConfig.setMode(2);
                                } else {
                                    if (isAlphabetic) {
                                        listConfig.setMode(1);
                                    } else {
                                        listConfig.setMode(0);
                                    }
                                }

                                iDialogProviderCallback.onListConfigCallback(listConfig, CREATE_NEW_LIST_CODE);

                                dialog.dismiss();
                            }
                        })
                        .build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        numericCheckbox = dialog.getCustomView().findViewById((R.id.numeric_mode_ckeckbox));
        alphabeticCheckbox = dialog.getCustomView().findViewById(R.id.alphabetic_mode_ckeckbox);
        nameInput = dialog.getCustomView().findViewById(R.id.create_new_list_name_textedit);

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean lengthNotZero = charSequence.toString().trim().length() > 0;
                if (lengthNotZero && StringValidatorHelper.filenameLegal(charSequence.toString())) {
                    positiveAction.setEnabled(true);
                } else {
                    positiveAction.setEnabled(false);
                    //TODO Show toast
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        numericCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b && !alphabeticCheckbox.isChecked()) {
                    positiveAction.setEnabled(false);
                } else {
                    positiveAction.setEnabled(true);
                }
            }
        });

        alphabeticCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b && !numericCheckbox.isChecked()) {
                    positiveAction.setEnabled(false);
                } else {
                    positiveAction.setEnabled(true);
                }
            }
        });

        positiveAction.setEnabled(false);

        dialog.show();
    }

    public void showStatsDialog(int allItemsCount, int displayedItemsCount){
        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.stat_dialog_all_items))
                .append(": ")
                .append(String.valueOf(allItemsCount))
                .append("\n")
                .append(context.getString(R.string.stat_dialog_displayed_items))
                .append(": ")
                .append(String.valueOf(displayedItemsCount));

        new MaterialDialog.Builder(context)
                .title(context.getString(R.string.stat_dialog_title))
                .content(sb.toString())
                .positiveText(context.getString(R.string.dialog_positive_button))
                .negativeText(context.getString(R.string.dialog_negative_button))
                .show();
    }
}

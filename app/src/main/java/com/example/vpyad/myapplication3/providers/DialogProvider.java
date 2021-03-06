package com.example.vpyad.myapplication3.providers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vpyad.myapplication3.R;
import com.example.vpyad.myapplication3.helpers.StringValidatorHelper;
import com.example.vpyad.myapplication3.models.ListConfig;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by vpyad on 06-Jan-18.
 */

public class DialogProvider {

    public static final int OPEN_FILE_CODE = 1;
    public static final int SAVE_ON_BACK_PRESSED_FILE_CODE = 2;
    public static final int DELETE_ITEM_CODE = 3;
    public static final int CLEAR_ALL_CODE = 4;
    public static final int APPLY_MODE_CODE = 5;
    public static final int APPLY_SORT_CODE = 6;
    public static final int CREATE_NEW_LIST_CODE = 7;
    public static final int FILE_CHOSSER_CODE = 8;

    private Context context;
    private final IDialogProviderCallback iDialogProviderCallback;

    public DialogProvider(Context context, final IDialogProviderCallback iDialogProviderCallback) {
        this.context = context;
        this.iDialogProviderCallback = iDialogProviderCallback;
    }

    public void showModeDialog(int currentMode) {
        Collection<String> modes = Arrays.asList(context.getString(R.string.mode_numeric), context.getString(R.string.mode_alphabetic));
        final Integer[] selectedIndxs;

        if (currentMode == ListConfig.MODE_MIXED) {
            selectedIndxs = new Integer[]{0, 1};
        } else {
            selectedIndxs = new Integer[]{currentMode};
        }

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
                        return which.length >= 1;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Integer[] selected = dialog.getSelectedIndices();

                        if (!Arrays.equals(selected, selectedIndxs)) {

                            int mode;

                            if (selected.length == 2) {
                                mode = ListConfig.MODE_MIXED;
                            } else {
                                mode = selected[0];
                            }

                            iDialogProviderCallback.onResultCodeCallback(APPLY_MODE_CODE, mode);
                        }
                    }
                })
                .show();
    }

    public void showSortDialog(final int currentSort) {
        Collection<String> sorts = Arrays.asList(context.getString(R.string.sort_no_sort), context.getString(R.string.sort_asc), context.getString(R.string.sort_desc));
        new MaterialDialog.Builder(context)
                .title(context.getString(R.string.sort_dialog_title))
                .items(sorts)
                .autoDismiss(true)
                .positiveText(context.getString(R.string.dialog_positive_button))
                .negativeText(context.getString(R.string.dialog_negative_button))
                .itemsCallbackSingleChoice(currentSort, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (dialog.getSelectedIndex() != currentSort) {

                            iDialogProviderCallback.onResultCodeCallback(APPLY_SORT_CODE, dialog.getSelectedIndex());
                        }
                    }
                })
                .show();
    }

    public void saveOnOpenFileDialog() {
        new MaterialDialog.Builder(context)
                .title(context.getString(R.string.open_file_dialog_title))
                .content(context.getString(R.string.open_file_dialog_message))
                .positiveText(context.getString(R.string.open_file_dialog_possitive_button))
                .negativeText(context.getString(R.string.open_file_dialog_negative_button))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.OPEN_FILE_CODE, true);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.OPEN_FILE_CODE, false);
                    }
                })
                .show();
    }

    public void showSaveOnBackPressedFileDialog() {
        new MaterialDialog.Builder(context)
                .title(context.getString(R.string.save_dialog_title))
                .content(context.getString(R.string.save_dialog_message))
                .positiveText(context.getString(R.string.save_dialog_possitive_button))
                .negativeText(context.getString(R.string.save_dialog_negative_button))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.SAVE_ON_BACK_PRESSED_FILE_CODE, true);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.SAVE_ON_BACK_PRESSED_FILE_CODE, false);
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
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.CLEAR_ALL_CODE, true);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.CLEAR_ALL_CODE, false);
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
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.DELETE_ITEM_CODE, true);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        iDialogProviderCallback.onYesNoCallback(DialogProvider.DELETE_ITEM_CODE, false);
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
                    Toast.makeText(context, context.getString(R.string.invalid_list_name_text), Toast.LENGTH_SHORT).show();
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

    public void showStatsDialog(int allItemsCount, int displayedItemsCount) {
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

    public void showFileChosserDialog() {
        DialogProperties properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = new String[]{".lbm"};

        FilePickerDialog dialog = new FilePickerDialog(context, properties);

        dialog.setTitle(context.getString(R.string.file_chooser_dialog_title));
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                String path = files[0];
                iDialogProviderCallback.onFileChooserCallback(path, FILE_CHOSSER_CODE);
            }
        });

        dialog.show();
    }
}

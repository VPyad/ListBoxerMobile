package com.example.vpyad.myapplication3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vpyad.myapplication3.helpers.StorageHelper;
import com.example.vpyad.myapplication3.models.ListConfig;
import com.example.vpyad.myapplication3.providers.DialogProvider;
import com.example.vpyad.myapplication3.providers.ListConfigProvider;
import com.example.vpyad.myapplication3.providers.IDialogProviderCallback;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements IDialogProviderCallback {

    private static final int FILE_SELECT_CODE = 0;
    private static final int DIR_SELECT_CODE = 1;

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                WRITE_EXTERNAL_STORAGE_CODE);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_CODE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to write your External storage", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                saveConfigCache();
                //Toast.makeText(getApplicationContext(), "Add clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_open:
                showFileChooser2();
                //Toast.makeText(getApplicationContext(), "Open clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_save:
                loadConfigCache();
                //Toast.makeText(getApplicationContext(), "Save clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_sort:
                Toast.makeText(getApplicationContext(), getApplicationContext().getCacheDir().toURI().toString(), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_mode:
                //DoFuck();
                saveConfigDir(StorageHelper.getPathToSave());
                //Toast.makeText(getApplicationContext(), "Mode clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_stat:
                Toast.makeText(getApplicationContext(), "Stat clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_clear_all:
                showYesNoDialog();
                //showSortDialog2();
                //showModeDialog2();
                //showSortDialog();
                //showModeDialog();
                //Toast.makeText(getApplicationContext(), "Clear all clicked", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    File file = new File(data.getData().toString());

                    boolean fuckIt = file.exists();

                    String path = file.getAbsolutePath();

                    loadConfigDir(path);
                    //Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
                }
                break;
            case DIR_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    String path = data.getExtras().getString("data");

                    saveConfigDir(path);
                    //Toast.makeText(getApplicationContext(), data.getData().toString(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void saveConfigCache() {
        ListConfig config = new ListConfig();

        config.setName("FuuuuckIt");
        config.setMode(2);

        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        config.setList(list);

        if (ListConfigProvider.setListConfigToCache(config, getApplicationContext().getCacheDir().toString()))
            Toast.makeText(getApplicationContext(), "Config Saved", Toast.LENGTH_LONG).show();
    }

    private void saveConfigDir(String path) {
        ListConfig config = new ListConfig();

        config.setName("FuuuuckIt");
        config.setMode(2);

        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        config.setList(list);

        if (ListConfigProvider.setListConfigToDir(config, path))
            Toast.makeText(getApplicationContext(), "Config Saved", Toast.LENGTH_LONG).show();
    }

    private void loadConfigCache() {
        ListConfig config = ListConfigProvider.getListConfigFromCache(getApplicationContext().getCacheDir().toString());

        if (config != null)
            Toast.makeText(getApplicationContext(), config.getName() + " " + config.getList().get(0), Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Config failed to load", Toast.LENGTH_LONG).show();
    }

    private void loadConfigDir(String path) {
        ListConfig config = ListConfigProvider.getListConfigFromDir(path);

        if (config != null)
            Toast.makeText(getApplicationContext(), config.getName() + " " + config.getList().get(0), Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Config failed to load", Toast.LENGTH_LONG).show();
    }

    private void showFileChooser2() {
        DialogProperties properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = new String[]{".lbm"};

        FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);

        dialog.setTitle("Select a File");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                String path = files[0];
                loadConfigDir(path);
            }
        });

        dialog.show();
    }

    private int fuckIt = 5;

    private void showModeDialog() {
        Collection<String> modes = Arrays.asList("numeric", "alphabetic");

        int damn = 7;

        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .title(getString(R.string.mode_dialog_title))
                .items(modes)
                .autoDismiss(true)
                .positiveText("Ok")
                .negativeText("Cancel")
                .alwaysCallMultiChoiceCallback()
                .itemsCallbackMultiChoice(new Integer[]{0}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        boolean allowSelectionChange = which.length >= 1;
                        return allowSelectionChange;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();

        Integer[] selected = mDialog.getSelectedIndices();
    }

    private void showModeDialog2() {
        DialogProvider dialogProvider = new DialogProvider(this, this);

        ListConfig listConfig = new ListConfig();
        listConfig.setMode(0);

        dialogProvider.showModeDialog(listConfig);
    }

    private void showSortDialog() {
        Collection<String> sorts = Arrays.asList("no sort", "asc", "desc");

        new MaterialDialog.Builder(this)
                .title("Choose sorting")
                .items(sorts)
                .itemsCallbackSingleChoice(1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        return true;
                    }
                })
                .positiveText("Ok")
                .positiveText("Cancel")
                .show();
    }

    private void showSortDialog2(){
        DialogProvider dialogProvider = new DialogProvider(this, this);

        ListConfig listConfig = new ListConfig();
        listConfig.setSort(1);

        dialogProvider.showSortDialog(listConfig);

    }

    private void showYesNoDialog(){
        DialogProvider dialogProvider = new DialogProvider(this, this);

        dialogProvider.showDeleteItemDialog();
    }

    @Override
    public void onListConfigCallback(ListConfig res) {
        Toast.makeText(this, "1213", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onYesNoCallback(Integer res) {

    }
}

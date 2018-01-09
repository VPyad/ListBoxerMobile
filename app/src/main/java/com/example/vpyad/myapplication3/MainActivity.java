package com.example.vpyad.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vpyad.myapplication3.adapters.ItemsListAdapter;
import com.example.vpyad.myapplication3.helpers.RequestPermissionHelper;
import com.example.vpyad.myapplication3.helpers.StorageHelper;
import com.example.vpyad.myapplication3.models.ListConfig;
import com.example.vpyad.myapplication3.models.ListItem;
import com.example.vpyad.myapplication3.providers.DialogProvider;
import com.example.vpyad.myapplication3.providers.ListConfigProvider;
import com.example.vpyad.myapplication3.providers.IDialogProviderCallback;
import com.example.vpyad.myapplication3.providers.LocalStorageProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IDialogProviderCallback, AdapterView.OnItemClickListener {

    private LocalStorageProvider localStorageProvider;
    private DialogProvider dialogProvider;
    private ListConfig listConfig;
    private RecyclerView recyclerView;
    private ItemsListAdapter itemsAdapter;
    private EditText itemInputText;
    private ListItem itemToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localStorageProvider = new LocalStorageProvider(MainActivity.this);
        dialogProvider = new DialogProvider(this, this);
        listConfig = ListConfigProvider.getListConfig(localStorageProvider.getString(LocalStorageProvider.CURRENT_LIST_PATH), MainActivity.this);

        RequestPermissionHelper.requestWriteExternalStoragePermission(MainActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.listRecyclerView);
        itemInputText = findViewById(R.id.inputEditText);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        addTrashItems();
        updateItemsAdapter();

        itemsAdapter.notifyDataSetChanged();
    }

    //TODO !!!Remove!!!
    private void addTrashItems() {
        listConfig.addToList(new ListItem("Apple", ListConfig.MODE_ALPHABETIC));
        listConfig.addToList(new ListItem("Orange", ListConfig.MODE_ALPHABETIC));
        listConfig.addToList(new ListItem("Apple1", ListConfig.MODE_MIXED));
        listConfig.addToList(new ListItem("Orange!", ListConfig.MODE_MIXED));
        listConfig.addToList(new ListItem("123", ListConfig.MODE_NUMERIC));
        listConfig.addToList(new ListItem("456", ListConfig.MODE_NUMERIC));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionHelper.WRITE_EXTERNAL_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    localStorageProvider.putBool(LocalStorageProvider.WRITE_PERMISSION_KEY, true);
                } else {
                    localStorageProvider.putBool(LocalStorageProvider.WRITE_PERMISSION_KEY, false);
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
                dialogProvider.showCreateNewListDialog();
                return true;
            case R.id.action_open:
                openFile();
                return true;
            case R.id.action_save:
                saveToFile();
                return true;
            case R.id.action_sort:
                dialogProvider.showSortDialog(listConfig);
                return true;
            case R.id.action_mode:
                dialogProvider.showModeDialog(listConfig);
                return true;
            case R.id.action_stat:
                // TODO Edit when RecyclerView ready
                dialogProvider.showStatsDialog(10, 5);
                return true;
            case R.id.action_clear_all:
                dialogProvider.showClearAllDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onListConfigCallback(ListConfig res, Integer code) {
        switch (code) {
            case DialogProvider.CREATE_NEW_LIST_CODE:
                listConfig = res;
                ListConfigProvider.setListConfigToDir(res, StorageHelper.getPathToSave(), MainActivity.this);
                updateItemsAdapter();
                hideKeyboard();
                clearInputText();
                break;
            case DialogProvider.APPLY_MODE_CODE:
                listConfig.setMode(res.getMode());
                listConfig.filterList(res.getMode());
                updateItemsAdapter();
                break;
            case DialogProvider.APPLY_SORT_CODE:
                int n = res.getSort();
                listConfig.setSort(res.getSort());
                listConfig.sortList(res.getSort());
                updateItemsAdapter();
                break;
        }
    }

    @Override
    public void onYesNoCallback(Integer res, boolean commitAction) {
        switch (res) {
            case DialogProvider.OPEN_FILE_CODE:
                if (commitAction) {
                    boolean saved = ListConfigProvider.setListConfigToDir(listConfig, StorageHelper.getPathToSave(), MainActivity.this);
                    if (!saved) {
                        makeToast(getString(R.string.save_failed_text));
                    }
                }
                dialogProvider.showFileChosserDialog();
                break;
            case DialogProvider.SAVE_FILE_CODE:
                // TODO save file
                break;
            case DialogProvider.DELETE_ITEM_CODE:
                if (commitAction && itemToDelete != null) {
                    listConfig.removeItem(itemToDelete);
                    updateItemsAdapter();
                }
                // TODO delete item from RecyclerView
                break;
            case DialogProvider.CLEAR_ALL_CODE:
                // TODO Clear all
                break;
        }
    }

    @Override
    public void onFileChooserCallback(String path, Integer res) {
        switch (res) {
            case DialogProvider.FILE_CHOSSER_CODE:
                ListConfig listConfig = ListConfigProvider.getListConfigFromDir(path);
                if (listConfig != null) {
                    this.listConfig = listConfig;
                    localStorageProvider.putString(LocalStorageProvider.CURRENT_LIST_PATH, path);
                    updateItemsAdapter();
                    hideKeyboard();
                    clearInputText();
                } else {
                    makeToast(getString(R.string.open_failed_text));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (hasUnsaveData()) {
            dialogProvider.showSaveFileDialog();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        /*if(needToSave()){
            ListConfigProvider.setListConfigToDir(listConfig, localStorageProvider.getString(LocalStorageProvider.CURRENT_LIST_PATH));
        }*/
        super.onPause();
    }

    @Override
    public void onResume() {
        //listConfig = ListConfigProvider.getListConfig(localStorageProvider.getString(LocalStorageProvider.CURRENT_LIST_PATH), MainActivity.this);
        super.onResume();
    }

    private void saveToFile() {
        if (localStorageProvider.getBool(LocalStorageProvider.WRITE_PERMISSION_KEY)) {
            if (ListConfigProvider.setListConfigToDir(listConfig, StorageHelper.getPathToSave(), MainActivity.this)) {
                makeToast(getString(R.string.save_completed_text));
            } else {
                makeToast(getString(R.string.save_failed_text));
            }
        } else {
            makeToast(getString(R.string.no_write_permission_text));
        }
    }

    private void openFile() {
        if (hasUnsaveData()) {
            dialogProvider.showOpenFileDialog();
        } else if (localStorageProvider.getBool(LocalStorageProvider.WRITE_PERMISSION_KEY)) {
            dialogProvider.showFileChosserDialog();
        } else {
            makeToast(getString(R.string.no_write_permission_text));
        }
    }

    private void makeToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    private boolean hasUnsaveData() {
        ListConfig savedListConfig = ListConfigProvider.getListConfigFromDir(localStorageProvider.getString(LocalStorageProvider.CURRENT_LIST_PATH));

        boolean isSavedNull = savedListConfig == null;

        if (!isSavedNull) {
            boolean eq = (listConfig.getList().containsAll(savedListConfig.getList()) && savedListConfig.getMode() == listConfig.getMode()
                    && savedListConfig.getSort() == listConfig.getSort());
            return !eq;
        } else {
            return false;
        }
    }

    public void onAddItemButtonClicked(View view) {
        listConfig.addToList(new ListItem(itemInputText.getText().toString(), ListConfig.MODE_MIXED));
        itemsAdapter.notifyDataSetChanged();
        clearInputText();
    }

    private void updateItemsAdapter() {
        itemsAdapter = new ItemsListAdapter(this, listConfig.getList());
        recyclerView.setAdapter(itemsAdapter);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void clearInputText() {
        itemInputText.setText("");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ListItem item = itemsAdapter.getItem(i);
        itemToDelete = item;

        dialogProvider.showDeleteItemDialog();
    }
}

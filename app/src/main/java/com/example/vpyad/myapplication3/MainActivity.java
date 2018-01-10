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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vpyad.myapplication3.adapters.ItemsListAdapter;
import com.example.vpyad.myapplication3.helpers.RequestPermissionHelper;
import com.example.vpyad.myapplication3.helpers.StorageHelper;
import com.example.vpyad.myapplication3.helpers.StringValidatorHelper;
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
    private boolean hasChange = false;

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

        addTrashItems2();
        updateItemsAdapter();

        //itemsAdapter.notifyDataSetChanged();
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

    private void addTrashItems2(){
        listConfig.addToList(new ListItem("Apple", ListConfig.MODE_ALPHABETIC));
        listConfig.addToList(new ListItem("Apricot", ListConfig.MODE_ALPHABETIC));
        listConfig.addToList(new ListItem("Banana", ListConfig.MODE_ALPHABETIC));
        listConfig.addToList(new ListItem("Blueberry", ListConfig.MODE_ALPHABETIC));
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
                dialogProvider.showSortDialog(listConfig.getSort());
                return true;
            case R.id.action_mode:
                dialogProvider.showModeDialog(listConfig.getMode());
                return true;
            case R.id.action_stat:
                dialogProvider.showStatsDialog(listConfig.allItemsCount(), listConfig.filteredItemsCount());
                return true;
            case R.id.action_clear_all:
                dialogProvider.showClearAllDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListConfigCallback(ListConfig res, Integer code) {
        switch (code) {
            case DialogProvider.CREATE_NEW_LIST_CODE:
                listConfig = res;
                ListConfigProvider.setListConfigToDir(res, StorageHelper.getPathToSave(), MainActivity.this);
                hasChange = false;
                updateItemsAdapter();
                hideKeyboard();
                clearInputText();
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
                    hasChange = false;
                }
                dialogProvider.showFileChosserDialog();
                break;
            case DialogProvider.DELETE_ITEM_CODE:
                if (commitAction && itemToDelete != null) {
                    listConfig.removeItem(itemToDelete);
                    hasChange = true;
                    updateItemsAdapter();
                }
                break;
            case DialogProvider.CLEAR_ALL_CODE:
                listConfig.removeAll();
                hasChange = true;
                updateItemsAdapter();
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
                    hasChange = false;
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
    public void onResultCodeCallback(int actionCode, int payloadCode) {
        switch (actionCode) {
            case DialogProvider.APPLY_MODE_CODE:
                listConfig.setMode(payloadCode);
                updateItemsAdapter();
                break;
            case DialogProvider.APPLY_SORT_CODE:
                listConfig.setSort(payloadCode);
                updateItemsAdapter();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (hasChange) {
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
        if (hasChange) {
            dialogProvider.saveOnOpenFileDialog();
        } else if (localStorageProvider.getBool(LocalStorageProvider.WRITE_PERMISSION_KEY)) {
            dialogProvider.showFileChosserDialog();
        } else {
            makeToast(getString(R.string.no_write_permission_text));
        }
    }

    private void makeToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    public void onAddItemButtonClicked(View view) {
        if(!isInputLegal(itemInputText.getText().toString())){
            makeToast(getString(R.string.invalid_input));
            return;
        }

        listConfig.addToList(new ListItem(itemInputText.getText().toString(), listConfig.getMode()));
        hasChange = true;
        updateItemsAdapter();
        listConfig.sortList();
        clearInputText();
    }

    private void updateItemsAdapter() {
        itemsAdapter = new ItemsListAdapter(this, listConfig.getList());
        recyclerView.swapAdapter(new ItemsListAdapter(this, listConfig.getList()), true);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void clearInputText() {
        itemInputText.setText("");
    }

    private boolean isInputLegal(String s) {
        switch (listConfig.getMode()) {
            case ListConfig.MODE_ALPHABETIC:
                return StringValidatorHelper.allLetters(s);
            case ListConfig.MODE_MIXED:
                return StringValidatorHelper.allLettersAndDigits(s);
            case ListConfig.MODE_NUMERIC:
                return StringValidatorHelper.allDigits(s);
            default:
                return false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ListItem item = itemsAdapter.getItem(i);
        itemToDelete = item;

        dialogProvider.showDeleteItemDialog();
    }
}
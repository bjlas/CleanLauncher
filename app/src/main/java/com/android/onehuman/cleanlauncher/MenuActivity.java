package com.android.onehuman.cleanlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.onehuman.cleanlauncher.adapter.HomeAdapter;
import com.android.onehuman.cleanlauncher.adapter.MenuAdapter;
import com.android.onehuman.cleanlauncher.events.HomeItemTouchHelper;
import com.android.onehuman.cleanlauncher.interfaces.OnTaskCompleted;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.Header;
import com.android.onehuman.cleanlauncher.persistence.DBController;
import com.android.onehuman.cleanlauncher.task.PopulateDBAppsTask;
import com.android.onehuman.cleanlauncher.utils.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MenuActivity extends AppCompatActivity implements OnTaskCompleted {

    static Activity activity;

    private List<RowType> menuAppsList;
    private MenuAdapter menuAdapter;
    private RecyclerView menuRecyclerView;
    PackageManager packageManager;
    private String selectHeader;
    private LinearLayoutManager linearLayoutManager;
    private AppUtils appUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        activity=this;
        appUtils = new AppUtils(activity);
        packageManager =getPackageManager();
        menuRecyclerView = (RecyclerView) findViewById(R.id.menu_listview);
        linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        menuRecyclerView.setLayoutManager(linearLayoutManager);
        new PopulateDBAppsTask(activity, this).execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            selectHeader = data.getStringExtra("selectedHeader");
            int position = menuAppsList.indexOf(new Header(selectHeader));
            linearLayoutManager.scrollToPositionWithOffset(position,0);
        }
    }

    @Override
    public void onTaskCompleted() {
        menuAppsList = appUtils.generateMenuList();
        menuAdapter = new MenuAdapter(activity, menuAppsList);
        menuRecyclerView.setAdapter(menuAdapter);
    }

}

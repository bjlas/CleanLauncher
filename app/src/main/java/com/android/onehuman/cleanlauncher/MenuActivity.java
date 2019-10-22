package com.android.onehuman.cleanlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;


import com.android.onehuman.cleanlauncher.adapter.MenuAdapter;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.Header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.android.onehuman.cleanlauncher.utils.Utils.generateMenuList;
import static com.android.onehuman.cleanlauncher.utils.Utils.isHeader;

public class MenuActivity extends AppCompatActivity {

    static Activity activity;

    private List<RowType> menuAppsList;
    private MenuAdapter menuAdapter;
    private RecyclerView menuRecyclerView;
    PackageManager packageManager;
    private String selectHeader;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        activity=this;
        packageManager =getPackageManager();
        menuRecyclerView = (RecyclerView) findViewById(R.id.menu_listview);
        linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        menuRecyclerView.setLayoutManager(linearLayoutManager);
        new initAppsList().execute();

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
            linearLayoutManager.scrollToPositionWithOffset(findPosition(selectHeader),0);
        }
    }

    public int findPosition(String headerLabel) {
        int index=0;
        for(index=0; index<menuAppsList.size(); index++) {
            if(menuAppsList.get(index) instanceof Header) {
                if (((Header) menuAppsList.get(index)).getLabel().equals(headerLabel)) {
                    return index;
                }
            }
        }
        return index;
    }


    public class initAppsList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> pacsList = packageManager.queryIntentActivities(mainIntent, 0);
            Collections.sort(pacsList, new ResolveInfo.DisplayNameComparator(packageManager));
            menuAppsList = new ArrayList<>();
            menuAppsList = generateMenuList(pacsList, packageManager);
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            menuAdapter = new MenuAdapter(activity, menuAppsList);
            menuRecyclerView.setAdapter(menuAdapter);

        }

    }

}

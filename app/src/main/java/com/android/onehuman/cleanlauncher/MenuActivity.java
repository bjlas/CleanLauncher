package com.android.onehuman.cleanlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.android.onehuman.cleanlauncher.adapter.MenuAdapter;
import com.android.onehuman.cleanlauncher.events.Menu_OnItemClickListener;
import com.android.onehuman.cleanlauncher.model.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    static Activity activity;

    private List<App> menuAppsList;
    private MenuAdapter menuAdapter;
    private ListView menuListView;
    PackageManager packageManager;
    Menu_OnItemClickListener menu_onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        menuListView = (ListView) findViewById(R.id.menu_listview);

        activity=this;
        packageManager =getPackageManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new initAppsList().execute();
    }

    public class initAppsList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> pacsList = packageManager.queryIntentActivities(mainIntent, 0);
            Collections.sort(pacsList, new ResolveInfo.DisplayNameComparator(packageManager));
            menuAppsList = new ArrayList<>();

            for(int I=0;I<pacsList.size();I++){
                App app = new App(
                        pacsList.get(I).loadLabel(packageManager).toString(),
                        pacsList.get(I).activityInfo.name,
                        pacsList.get(I).activityInfo.packageName

                );
                menuAppsList.add(app);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){

                menuAdapter = new MenuAdapter(activity, menuAppsList);
                menuListView.setAdapter(menuAdapter);

                menu_onItemClickListener = new Menu_OnItemClickListener(activity, menuAppsList);
                menuListView.setOnItemClickListener(menu_onItemClickListener);

        }

    }

}

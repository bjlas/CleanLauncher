package com.android.onehuman.cleanlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.onehuman.cleanlauncher.adapter.MenuAdapter;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.Header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    static Activity activity;

    private List<RowType> menuAppsList;
    private MenuAdapter menuAdapter;
    private RecyclerView menuRecyclerView;
    PackageManager packageManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        activity=this;
        packageManager =getPackageManager();
        menuRecyclerView = (RecyclerView) findViewById(R.id.menu_listview);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        new initAppsList().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public class initAppsList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> pacsList = packageManager.queryIntentActivities(mainIntent, 0);
            Collections.sort(pacsList, new ResolveInfo.DisplayNameComparator(packageManager));
            menuAppsList = new ArrayList<>();

            String previous="";
            for(int index=0;index<pacsList.size();index++){
                String label = pacsList.get(index).loadLabel(packageManager).toString();

                if(index == 0 || isHeader(label, previous)) {
                    previous=label.subSequence(0, 1).toString();
                    menuAppsList.add(new Header(previous));
                }

                App app = new App(
                        label,
                        pacsList.get(index).activityInfo.name,
                        pacsList.get(index).activityInfo.packageName
                );
                menuAppsList.add(app);
            }
            return null;
        }

        public boolean isHeader(String label, String previous) {
            return label.charAt(0) != previous.charAt(0);
        }




        @Override
        protected void onPostExecute(String result){
            menuAdapter = new MenuAdapter(activity, menuAppsList);
            menuRecyclerView.setAdapter(menuAdapter);
        }



    }

}

package com.android.onehuman.cleanlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.onehuman.cleanlauncher.adapter.MenuAdapter;
import com.android.onehuman.cleanlauncher.events.Menu_OnItemClickListener;
import com.android.onehuman.cleanlauncher.interfaces.SectionCallback;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.MenuHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    static Activity activity;

    private List<App> menuAppsList;
    private MenuAdapter menuAdapter;
    private RecyclerView menuRecyclerView;
    PackageManager packageManager;
    Menu_OnItemClickListener menu_onItemClickListener;

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
            menuRecyclerView.setAdapter(menuAdapter);
            MenuHeader menuHeaderList = new MenuHeader(getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height), true, getSectionCallback(menuAppsList));
            menuRecyclerView.addItemDecoration(menuHeaderList);
        }

        private SectionCallback getSectionCallback(final List<App> apps) {
            return new SectionCallback() {
                @Override
                public boolean isSection(int position) {
                    return position == 0 || apps.get(position)
                            .getLabel()
                            .charAt(0) != apps.get(position - 1)
                            .getLabel()
                            .charAt(0);
                }

                @Override
                public CharSequence getSectionHeader(int position) {
                    return apps.get(position)
                            .getLabel()
                            .subSequence(0, 1);
                }
            };
        }

    }

}

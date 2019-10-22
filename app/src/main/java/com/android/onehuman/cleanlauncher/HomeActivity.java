package com.android.onehuman.cleanlauncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.adapter.HomeAdapter;
import com.android.onehuman.cleanlauncher.events.HomeItemTouchHelper;
import com.android.onehuman.cleanlauncher.interfaces.OnHomeClickListener;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.LauncherApp;
import com.android.onehuman.cleanlauncher.model.Notification;
import com.android.onehuman.cleanlauncher.persistence.DBController;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements OnHomeClickListener {

    public static boolean appLaunchable = true;
    private RecyclerView homeRecyclerView;
            private ArrayList<RowType> homeAppsList  = new ArrayList<>();

            private HomeAdapter homeAdapter;
            DBController dbController;
            Activity activity;
            PackageManager packageManager;
    private NotificationReceiver nReceiver;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.home_activity);
                activity=this;
                packageManager =getPackageManager();
                homeRecyclerView = (RecyclerView) findViewById(R.id.home_recyclerview);
                LinearLayoutManager homeRecyclerViewLayoutManager = new LinearLayoutManager(this);
                homeRecyclerView.setLayoutManager(homeRecyclerViewLayoutManager);
                dbController = new DBController(activity);


                if (!NotificationManagerCompat.getEnabledListenerPackages(this).contains(getPackageName())) {
                    Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    startActivity(intent);
                }

                nReceiver = new NotificationReceiver();
                IntentFilter filter = new IntentFilter();
                filter.addAction("com.android.onehuman.cleanlauncher.NOTIFICATION_MONITOR");
                filter.addCategory("NOTIFICATION_MONITOR.POSTED");
                filter.addCategory("NOTIFICATION_MONITOR.REMOVED");
                registerReceiver(nReceiver,filter);

            }

            @Override
            protected void onDestroy() {
                super.onDestroy();
                unregisterReceiver(nReceiver);
            }


            @Override
            protected void onResume() {

                super.onResume();
                loadAppsList();

                if(homeAdapter == null) {
                    homeAdapter = new HomeAdapter(activity, homeAppsList, this);
                    ItemTouchHelper.Callback callback = new HomeItemTouchHelper(homeAdapter);
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
                    homeAdapter.setTouchHelper(itemTouchHelper);
                    itemTouchHelper.attachToRecyclerView(homeRecyclerView);
                    homeRecyclerView.setAdapter(homeAdapter);
                } else {
                    homeAdapter.updateAppList(homeAppsList);
                }


            }

            public void loadAppsList()  {
                homeAppsList = dbController.getAllApps();

                for (RowType app : homeAppsList) {
                    if (app instanceof LauncherApp) {
                        String pn = ((LauncherApp) app).getPackageName();
                        if (!isPackageInstalled(pn)) {
                            dbController.deleteApp(pn);
                            homeAppsList.remove(app);
                        }
                    }
                }
            }

            private boolean isPackageInstalled(String packageName) {
                try {
                    packageManager.getPackageInfo(packageName, 0);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                    return false;
                }
            }

            public void loadMenu(View view) {
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
            }

    @Override
    public void onHomeClick(int position) {
        LauncherApp apptoLaunch = (LauncherApp) homeAppsList.get(position);
        if (HomeActivity.appLaunchable){
            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(apptoLaunch.getPackageName(), apptoLaunch.getName());
            launchIntent.setComponent(cp);

            activity.startActivity(launchIntent);
        }
    }


    public class NotificationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasCategory("NOTIFICATION_MONITOR.POSTED")) {
                if (intent.hasExtra("packageName")) {
                    String packageName = intent.getStringExtra("packageName");
                    dbController.insertNotification(packageName,1);
                    homeAppsList = dbController.getAllApps();
                    homeAdapter.updateAppList(homeAppsList);
                }
            }

            if (intent.hasCategory("NOTIFICATION_MONITOR.REMOVED")) {
                if (intent.hasExtra("packageName")) {
                    String packageName = intent.getStringExtra("packageName");
                    dbController.deleteNotification(packageName);
                }
            }
        }
    }

}

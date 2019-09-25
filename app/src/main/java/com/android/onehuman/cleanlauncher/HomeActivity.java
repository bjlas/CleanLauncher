package com.android.onehuman.cleanlauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.android.onehuman.cleanlauncher.adapter.HomeAdapter;
import com.android.onehuman.cleanlauncher.adapter.MenuAdapter;
import com.android.onehuman.cleanlauncher.events.HomeItemTouchHelper;
import com.android.onehuman.cleanlauncher.events.Menu_OnItemClickListener;
import com.android.onehuman.cleanlauncher.interfaces.OnHomeClickListener;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.persistence.DBController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

        public class HomeActivity extends AppCompatActivity implements OnHomeClickListener {

            public static boolean appLaunchable = true;
            private RecyclerView homeRecyclerView;
            private ArrayList<App> homeAppsList  = new ArrayList<>();
            private HomeAdapter homeAdapter;
            DBController dbController;
            Activity activity;
            PackageManager packageManager;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.home_activity);
                activity=this;
                packageManager =getPackageManager();
                homeRecyclerView = (RecyclerView) findViewById(R.id.home_recyclerview);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                homeRecyclerView.setLayoutManager(linearLayoutManager);
                dbController = new DBController(activity);


                if (!NotificationManagerCompat.getEnabledListenerPackages(this).contains(getPackageName())) {        //ask for permission
                    Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    startActivity(intent);
                }

                LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("NOTIFICATION_POSTED"));
                LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("NOTIFICATION_REMOVED"));


            }

            @Override
            protected void onDestroy() {
                super.onDestroy();
                LocalBroadcastManager.getInstance(this).unregisterReceiver(onNotice);

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
                homeAppsList = dbController.getAll();

                for (App app : homeAppsList) {
                    if (!isPackageInstalled(app.getPackageName())) {
                        dbController.delete(app.getPackageName());
                        homeAppsList.remove(app);
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
                App apptoLaunch = homeAppsList.get(position);
                if (HomeActivity.appLaunchable){
                    Intent launchIntent = new Intent(Intent.ACTION_MAIN);
                    launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cp = new ComponentName(apptoLaunch.getPackageName(), apptoLaunch.getName());
                    launchIntent.setComponent(cp);

                    activity.startActivity(launchIntent);
                }
            }

            private BroadcastReceiver onNotice= new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    if (intent.getAction().equals("NOTIFICATION_POSTED")) {
                        String packageName = intent.getStringExtra("package");
                        updateNotification(packageName, true);
                        homeAdapter.updateAppList(homeAppsList);
                    }

                    if (intent.getAction().equals("NOTIFICATION_REMOVED")) {
                        String packageName = intent.getStringExtra("package");
                        updateNotification(packageName, false);
                        homeAdapter.updateAppList(homeAppsList);
                    }

                }

                private void updateNotification(String packageName, boolean update) {
                    for(App app : homeAppsList) {
                        if(app.getPackageName().equals(packageName)) {
                            app.setNotification(update);
                        }
                    }
                }
            };

        }

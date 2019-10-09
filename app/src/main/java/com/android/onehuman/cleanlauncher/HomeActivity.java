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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.adapter.HomeAdapter;
import com.android.onehuman.cleanlauncher.events.HomeItemTouchHelper;
import com.android.onehuman.cleanlauncher.interfaces.OnHomeClickListener;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.Notification;
import com.android.onehuman.cleanlauncher.persistence.DBController;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnHomeClickListener {

            public static boolean appLaunchable = true;
            private RecyclerView homeRecyclerView;
            private ArrayList<RowType> homeAppsList  = new ArrayList<>();

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
                LinearLayoutManager homeRecyclerViewLayoutManager = new LinearLayoutManager(this);
                homeRecyclerView.setLayoutManager(homeRecyclerViewLayoutManager);
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

                for (RowType app : homeAppsList) {
                    String pn = ((App) app).getPackageName();
                    if (!isPackageInstalled(pn)) {
                        dbController.delete(pn);
                        homeAppsList.remove(app);
                    }
                }


                //homeAppsList.add(new Notification("Ajustes","App1","App1","9 October","11:04", "Notification1"));
                //homeAppsList.add(new Notification("Archivos","App2","App2","9 October","01:23", "Notification2"));
               // homeAppsList.add(new Notification("Dropbox","App3","App3","9 October","13:54", "Notification3"));


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
                App apptoLaunch = (App) homeAppsList.get(position);
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
                    String packageName = intent.getStringExtra("package");
                    String title = intent.getStringExtra("title");
                    String date = intent.getStringExtra("date");
                    String posttime = intent.getStringExtra("posttime");
                    String text = intent.getStringExtra("text");



                    if (intent.getAction().equals("NOTIFICATION_POSTED")) {
                        homeAppsList.add(new Notification(title, title, packageName, date,posttime,text ));
                        //dbController.increaseNotificationValue(packageName);
                    }

                    if (intent.getAction().equals("NOTIFICATION_REMOVED")) {
                        //dbController.clearNotifications(packageName);
                    }

                    //homeAppsList = dbController.getAll();
                    homeAdapter.updateAppList(homeAppsList);

                }


            };



        }

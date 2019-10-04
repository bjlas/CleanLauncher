package com.android.onehuman.cleanlauncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.adapter.HomeAdapter;
import com.android.onehuman.cleanlauncher.adapter.HomeNotificationAdapter;
import com.android.onehuman.cleanlauncher.events.HomeItemTouchHelper;
import com.android.onehuman.cleanlauncher.interfaces.OnHomeClickListener;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.persistence.DBController;

import java.util.ArrayList;

        public class HomeActivity extends AppCompatActivity implements OnHomeClickListener {

            public static boolean appLaunchable = true;
            private RecyclerView homeRecyclerView;
            RecyclerView homeNotificationsRecyclerView;
            private ArrayList<App> homeAppsList  = new ArrayList<>();
            private ArrayList<App> homeNotificationAppsList  = new ArrayList<>();

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
                homeNotificationsRecyclerView = (RecyclerView)findViewById(R.id.homeNotification_recyclerview);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                homeRecyclerView.setLayoutManager(linearLayoutManager);
                dbController = new DBController(activity);


                if (!NotificationManagerCompat.getEnabledListenerPackages(this).contains(getPackageName())) {        //ask for permission
                    Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    startActivity(intent);
                }

                LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("NOTIFICATION_POSTED"));
                LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("NOTIFICATION_REMOVED"));








                LinearLayoutManager HorizontalLayout = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                homeNotificationsRecyclerView.setLayoutManager(HorizontalLayout);

                homeNotificationAppsList.add(new App("App1","App1","App1",1,1));
                homeNotificationAppsList.add(new App("App2","App2","App2",2,2));
                homeNotificationAppsList.add(new App("App3","App3","App3",2,2));
                homeNotificationAppsList.add(new App("App1","App1","App1",1,1));
                homeNotificationAppsList.add(new App("App2","App2","App2",2,2));
                homeNotificationAppsList.add(new App("App3","App3","App3",2,2));
                homeNotificationAppsList.add(new App("App1","App1","App1",1,1));
                homeNotificationAppsList.add(new App("App2","App2","App2",2,2));
                homeNotificationAppsList.add(new App("App3","App3","App3",2,2));

                HomeNotificationAdapter homeNotificationAdapter = new HomeNotificationAdapter(homeNotificationAppsList);
                homeNotificationsRecyclerView.setAdapter(homeNotificationAdapter);

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
                    String packageName = intent.getStringExtra("package");


                    if (intent.getAction().equals("NOTIFICATION_POSTED")) {
                        getIcon(packageName);
                        dbController.increaseNotificationValue(packageName);
                    }

                    if (intent.getAction().equals("NOTIFICATION_REMOVED")) {
                        dbController.clearNotifications(packageName);
                    }

                    homeAppsList = dbController.getAll();
                    homeAdapter.updateAppList(homeAppsList);

                }


            };

            private void getIcon(String packageName) {
                Drawable appicon = null;
                try {
                    appicon = getPackageManager().getApplicationLogo(packageName);

                    ImageView noti = (ImageView) findViewById(R.id.homeNotification_recyclerview);

                    noti.setImageDrawable(appicon);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }

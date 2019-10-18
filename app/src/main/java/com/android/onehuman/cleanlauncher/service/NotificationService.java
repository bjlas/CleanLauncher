package com.android.onehuman.cleanlauncher.service;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {

    private NotificationMonitorReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new NotificationMonitorReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.onehuman.cleanlauncher.NOTIFICATION_MONITOR");
        registerReceiver(receiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
            Intent i = new Intent("com.android.onehuman.cleanlauncher.NOTIFICATION_MONITOR");
            i.addCategory("NOTIFICATION_MONITOR.POSTED");
            i.putExtra("packageName", sbn.getPackageName());
            sendBroadcast(i);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
            Intent i = new Intent("com.android.onehuman.cleanlauncher.NOTIFICATION_MONITOR");
            i.addCategory("NOTIFICATION_MONITOR.REMOVED");
            i.putExtra("packageName", sbn.getPackageName());
            sendBroadcast(i);
    }


    class NotificationMonitorReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getCategories() == null) {
                Intent i = new Intent("com.android.onehuman.cleanlauncher.NOTIFICATION_MONITOR");
                i.addCategory(intent.getCategories().toString());
                sendBroadcast(i);
            }
        }
    }





}
package com.android.onehuman.cleanlauncher.service;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationService extends NotificationListenerService {

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if ((sbn.getNotification().flags & Notification.FLAG_GROUP_SUMMARY) != 0) {
            Intent msgrcv = new Intent("NOTIFICATION_POSTED");
            msgrcv.putExtra("package", sbn.getPackageName());
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if ((sbn.getNotification().flags & Notification.FLAG_GROUP_SUMMARY) != 0) {
            Intent msgrcv = new Intent("NOTIFICATION_REMOVED");
            msgrcv.putExtra("package", sbn.getPackageName());
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
        }
    }
}
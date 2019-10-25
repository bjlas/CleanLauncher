package com.android.onehuman.cleanlauncher.events;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.android.onehuman.cleanlauncher.HomeActivity;
import com.android.onehuman.cleanlauncher.model.Notification;
import com.android.onehuman.cleanlauncher.utils.EventsUtils;

public class Notification_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;
    private Notification notification;

    public Notification_OnItemClickListener(Context c, Notification n) {
        this.context = c;
        this.notification=n;
    }

    @Override
    public void onClick(View view) {
        EventsUtils.launchApp(context, notification.getPackageName(), notification.getName());
    }

}

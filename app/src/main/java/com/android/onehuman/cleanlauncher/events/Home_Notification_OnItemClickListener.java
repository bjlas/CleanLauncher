package com.android.onehuman.cleanlauncher.events;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.android.onehuman.cleanlauncher.HomeActivity;
import com.android.onehuman.cleanlauncher.model.Notification;

public class Home_Notification_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;
    private Notification notification;

    public Home_Notification_OnItemClickListener(Context c, Notification n) {
        this.context = c;
        this.notification=n;
    }

    @Override
    public void onClick(View view) {

        if (HomeActivity.appLaunchable){
            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(notification.getPackageName(), notification.getName());
            launchIntent.setComponent(cp);

            context.startActivity(launchIntent);
        }

    }

}

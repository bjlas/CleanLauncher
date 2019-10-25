package com.android.onehuman.cleanlauncher.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.android.onehuman.cleanlauncher.HomeActivity;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.HomeApp;
import com.android.onehuman.cleanlauncher.model.MenuApp;
import com.android.onehuman.cleanlauncher.model.Notification;

public class EventsUtils {


    public static void launchApp(Context context, String packageName, String name) {
        if (HomeActivity.appLaunchable){
            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(packageName, name);
            launchIntent.setComponent(cp);

            context.startActivity(launchIntent);
        }
    }

}
package com.android.onehuman.cleanlauncher.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.onehuman.cleanlauncher.persistence.DBController;
import com.android.onehuman.cleanlauncher.task.PopulateDBAppsTask;
import com.android.onehuman.cleanlauncher.utils.AppUtils;

public class PackageReceiver extends BroadcastReceiver {

    private Context context;
    private DBController dbController;
    private AppUtils appUtils;

    public PackageReceiver(Context c) {
        context=c;
        dbController = new DBController(c);
        appUtils=new AppUtils(c);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getComponent().getPackageName();

        switch(intent.getAction())
        {
            case Intent.ACTION_PACKAGE_ADDED:

                appUtils.savePacsToDB();
                break;

            case Intent.ACTION_PACKAGE_REMOVED:
                dbController.deleteNotification(packageName);
                dbController.deleteHomeApp(packageName);
                dbController.deleteApp(packageName);
                break;
        }
    }
}

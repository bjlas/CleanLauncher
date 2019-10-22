package com.android.onehuman.cleanlauncher.events;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.android.onehuman.cleanlauncher.HomeActivity;
import com.android.onehuman.cleanlauncher.model.LauncherApp;

public class Menu_App_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;
    private LauncherApp app;

    public Menu_App_OnItemClickListener(Context c, LauncherApp a) {
        context = c;
        app=a;
    }

    @Override
    public void onClick(View view) {

        if (HomeActivity.appLaunchable){
            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(app.getPackageName(), app.getName());
            launchIntent.setComponent(cp);

            context.startActivity(launchIntent);
        }

    }

}

package com.android.onehuman.cleanlauncher.events;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.android.onehuman.cleanlauncher.HomeActivity;
import com.android.onehuman.cleanlauncher.model.App;

import java.util.List;

public class Menu_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;
    private App app;

    public Menu_OnItemClickListener(Context c, App a) {
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

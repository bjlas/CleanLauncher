package com.android.onehuman.cleanlauncher.events;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.android.onehuman.cleanlauncher.HomeActivity;
import com.android.onehuman.cleanlauncher.model.App;

import java.util.List;

public class Menu_OnItemClickListener implements AdapterView.OnItemClickListener {

    private Context context;
    private List<App> menuAppsList;

    public Menu_OnItemClickListener(Context c, List<App> al) {
        context = c;
        menuAppsList=al;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        App clickedApp = (App) menuAppsList.get(position);

        if (HomeActivity.appLaunchable){
            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(clickedApp.packageName, clickedApp.name);
            launchIntent.setComponent(cp);

            context.startActivity(launchIntent);
        }

    }

}

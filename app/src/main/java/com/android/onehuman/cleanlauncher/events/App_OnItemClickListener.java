package com.android.onehuman.cleanlauncher.events;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.HomeApp;
import com.android.onehuman.cleanlauncher.model.MenuApp;
import com.android.onehuman.cleanlauncher.utils.EventsUtils;

public class App_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;
    private App app;

    public App_OnItemClickListener(Context c, App a) {
        context = c;
        app=a;
    }

    @Override
    public void onClick(View view) {
        EventsUtils.launchApp(context, app.getPackageName(), app.getName());
    }

}

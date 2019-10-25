package com.android.onehuman.cleanlauncher.events;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import com.android.onehuman.cleanlauncher.model.HomeApp;
import com.android.onehuman.cleanlauncher.model.MenuApp;

public class Uninstall_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;
    private MenuApp app;

    public Uninstall_OnItemClickListener(Context c, MenuApp a) {
        context = c;
        app=a;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + app.getPackageName()));
        context.startActivity(intent);
    }

}

package com.android.onehuman.cleanlauncher.events;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.model.HomeApp;
import com.android.onehuman.cleanlauncher.model.MenuApp;
import com.android.onehuman.cleanlauncher.persistence.DBController;

public class AddToHome_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;
    private MenuApp app;
    private DBController dbController;

    public AddToHome_OnItemClickListener(Context c, MenuApp a) {
        context = c;
        app=a;
        dbController=new DBController(c);
    }

    @Override
    public void onClick(View v) {
        if(dbController.insertHomeApp(app.getPackageName()) >-1 ){
            Toast.makeText(context, context.getString(R.string.app_added_to_home)+app.getLabel(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getString(R.string.app_already_exist_at_home)+app.getLabel(), Toast.LENGTH_SHORT).show();
        }

    }
}

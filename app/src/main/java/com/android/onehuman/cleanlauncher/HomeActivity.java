package com.android.onehuman.cleanlauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.android.onehuman.cleanlauncher.adapter.HomeAdapter;
import com.android.onehuman.cleanlauncher.events.HomeItemTouchHelper;
import com.android.onehuman.cleanlauncher.interfaces.OnHomeClickListener;
import com.android.onehuman.cleanlauncher.interfaces.Persistence;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.persistence.DBController;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements OnHomeClickListener {

    public static boolean appLaunchable = true;
    private RecyclerView homeRecyclerView;
    private ArrayList<App> homeAppsList  = new ArrayList<>();
    private HomeAdapter homeAdapter;
    DBController dbController;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        activity=this;
        homeRecyclerView = (RecyclerView) findViewById(R.id.home_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        homeRecyclerView.setLayoutManager(linearLayoutManager);

        dbController = new DBController(activity);
        populateRecyclerView();

        homeAdapter = new HomeAdapter(homeAppsList, this);
        ItemTouchHelper.Callback callback = new HomeItemTouchHelper(homeAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        homeAdapter.setTouchHelper(itemTouchHelper);

        itemTouchHelper.attachToRecyclerView(homeRecyclerView);
        homeRecyclerView.setAdapter(homeAdapter);



    }

    private void populateRecyclerView() {

        long id1 = dbController.insert("Alarmas", "com.android.deskclock.AlarmClock", "com.android.deskclock");
        long id2 = dbController.insert("Gmail", "com.google.android.gm.ConversationListActivityGmail", "com.google.android.gm");
        long id3 = dbController.insert("Teléfono", "com.sonyericsson.android.socialphonebook.DialerEntryActivity", "com.sonyericsson.android.socialphonebook");

        homeAppsList = dbController.getAll();


    }

    public void loadMenu(View view) {
        Intent menuIntent = new Intent(this, MenuActivity.class);
        startActivity(menuIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onHomeClick(int position) {
        App apptoLaunch = homeAppsList.get(position);
        if (HomeActivity.appLaunchable){
            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(apptoLaunch.packageName, apptoLaunch.name);
            launchIntent.setComponent(cp);

            activity.startActivity(launchIntent);
        }
    }


}

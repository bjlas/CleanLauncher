package com.android.onehuman.cleanlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.onehuman.cleanlauncher.adapter.AlphabeticalAdapter;
import com.android.onehuman.cleanlauncher.adapter.MenuAdapter;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.Header;
import com.android.onehuman.cleanlauncher.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.android.onehuman.cleanlauncher.utils.Utils.generateAlphabeticalList;
import static com.android.onehuman.cleanlauncher.utils.Utils.generateMenuList;
import static com.android.onehuman.cleanlauncher.utils.Utils.isHeader;

public class AlphabeticalMenuActivity extends AppCompatActivity {

    private RecyclerView alphabeticalRecyclerView;
    private PackageManager packageManager;
    private List<Header> alphabeticalList;
    private AlphabeticalAdapter alphabeticalAdapter;
    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabetical_menu_activity);
        activity=this;
        packageManager =getPackageManager();

        alphabeticalRecyclerView = (RecyclerView) findViewById(R.id.alphabetical_listview);
        alphabeticalRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        new initAppsList().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public class initAppsList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> pacsList = packageManager.queryIntentActivities(mainIntent, 0);
            Collections.sort(pacsList, new ResolveInfo.DisplayNameComparator(packageManager));
            alphabeticalList = new ArrayList<Header>();
            alphabeticalList = generateAlphabeticalList(pacsList, packageManager);
            return null;
        }


        @Override
        protected void onPostExecute(String result){
            alphabeticalAdapter = new AlphabeticalAdapter(activity, alphabeticalList);
            alphabeticalRecyclerView.setAdapter(alphabeticalAdapter);
        }

    }

}

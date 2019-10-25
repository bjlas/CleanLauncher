package com.android.onehuman.cleanlauncher;

import android.app.Activity;
import android.os.Bundle;

import com.android.onehuman.cleanlauncher.adapter.AlphabetAdapter;
import com.android.onehuman.cleanlauncher.model.Header;
import com.android.onehuman.cleanlauncher.utils.AppUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AlphabetActivity extends AppCompatActivity {

    private RecyclerView alphabeticalRecyclerView;
    private List<Header> alphabeticalList;
    private AlphabetAdapter alphabetAdapter;
    private AppUtils appUtils;
    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabet_activity);
        activity=this;
        appUtils=new AppUtils(activity);

        alphabeticalRecyclerView = (RecyclerView) findViewById(R.id.alphabetical_listview);
        alphabeticalRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        initAlphabetList();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initAlphabetList() {
        alphabeticalList = appUtils.generateAlphabeticalList();
        alphabetAdapter = new AlphabetAdapter(activity, alphabeticalList);
        alphabeticalRecyclerView.setAdapter(alphabetAdapter);
    }

}

package com.android.onehuman.cleanlauncher.events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.onehuman.cleanlauncher.AlphabeticalMenuActivity;
import com.android.onehuman.cleanlauncher.MenuActivity;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.Header;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Menu_Header_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;
    private List<RowType> appList;

    public Menu_Header_OnItemClickListener(Context c, List<RowType> appList) {
        this.context = c;
        this.appList=appList;
    }

    @Override
    public void onClick(View view) {
        Intent alphabeticalMenuIntent = new Intent(context, AlphabeticalMenuActivity.class);
        ((Activity) context).startActivityForResult(alphabeticalMenuIntent,1);
    }

}

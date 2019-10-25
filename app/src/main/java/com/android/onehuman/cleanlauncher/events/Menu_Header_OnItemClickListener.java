package com.android.onehuman.cleanlauncher.events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.android.onehuman.cleanlauncher.AlphabetActivity;
import com.android.onehuman.cleanlauncher.interfaces.RowType;

import java.util.List;

public class Menu_Header_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;

    public Menu_Header_OnItemClickListener(Context c, List<RowType> appList) {
        this.context = c;

    }

    @Override
    public void onClick(View view) {
        Intent alphabeticalMenuIntent = new Intent(context, AlphabetActivity.class);
        ((Activity) context).startActivityForResult(alphabeticalMenuIntent,1);
    }

}

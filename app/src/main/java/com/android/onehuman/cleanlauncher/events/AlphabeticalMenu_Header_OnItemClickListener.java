package com.android.onehuman.cleanlauncher.events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.android.onehuman.cleanlauncher.AlphabeticalMenuActivity;
import com.android.onehuman.cleanlauncher.MenuActivity;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.Header;

import java.util.List;

public class AlphabeticalMenu_Header_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;
    private Header header;

    public AlphabeticalMenu_Header_OnItemClickListener(Context c, Header h) {
        this.context = c;
        this.header=h;
    }

    @Override
    public void onClick(View view) {
        Intent menuIntent = new Intent();
        menuIntent.putExtra("selectedHeader", header.getLabel());

        ((Activity)context).setResult(Activity.RESULT_OK, menuIntent);
        ((Activity)context).finish();
    }

}

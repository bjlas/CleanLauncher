package com.android.onehuman.cleanlauncher.events;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class Menu_Header_OnItemClickListener implements AdapterView.OnClickListener {

    private Context context;


    public Menu_Header_OnItemClickListener(Context c) {
        context = c;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context,"Header clicked",Toast.LENGTH_SHORT).show();
    }

}

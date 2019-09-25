package com.android.onehuman.cleanlauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.persistence.DBController;

import java.util.List;

public class MenuAdapter extends BaseAdapter {

    private List<App> appList;
    private Context context;
    private DBController dbController;

    public MenuAdapter(Context c, List<App> al) {
        context=c;
        this.appList = al;
        dbController = new DBController(c);
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return appList.get(position).hashCode();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final App app = (App) this.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_item, null);
        }

        TextView menu_item_label = (TextView) convertView.findViewById(R.id.menu_item_label);
        TextView addToHomeButton = (TextView) convertView.findViewById(R.id.menu_item_addToHome_button);
        TextView uninstallButton = (TextView) convertView.findViewById(R.id.menu_item_uninstall_button);

        menu_item_label.setText(app.getLabel());


        addToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dbController.insert(app.getLabel(), app.getName(), app.getPackageName())){
                    Toast.makeText(context, context.getString(R.string.app_added_to_home)+app.getLabel(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getString(R.string.app_already_exist_at_home)+app.getLabel(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        uninstallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO DELETE APP IF IT IS UNINSTALL
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + appList.get(position).getPackageName()));
                    context.startActivity(intent);
                }
        });

        return convertView;


    }


}
package com.android.onehuman.cleanlauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.events.Menu_OnItemClickListener;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.persistence.DBController;
import com.android.onehuman.cleanlauncher.viewHolders.MenuViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    private Context context;
    private final List<App> appList;
    private DBController dbController;

    public MenuAdapter(Context c, List<App> al) {
        this.context=c;
        this.appList = al;
        dbController=new DBController(c);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        final App app = appList.get(position);
        holder.label.setText(app.getLabel());


        holder.label.setOnClickListener(new Menu_OnItemClickListener(context, app));

        holder.addToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dbController.insert(app.getLabel(), app.getName(), app.getPackageName())){
                    Toast.makeText(context, context.getString(R.string.app_added_to_home)+app.getLabel(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getString(R.string.app_already_exist_at_home)+app.getLabel(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.uninstallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:" + app.getPackageName()));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return appList.size();
    }


}

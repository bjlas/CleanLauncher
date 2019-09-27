package com.android.onehuman.cleanlauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.events.Menu_OnItemClickListener;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.persistence.DBController;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MenuAdapter extends BaseAdapter {

    private List<App> appList = new ArrayList<>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    private Context context;
    private DBController dbController;


    private static final int HEADER_ITEM = 0;
    private static final int APP_ITEM = 1;


    public MenuAdapter(Context c) {
        context=c;
        dbController = new DBController(c);
    }

    public void addApp(final App item) {
        appList.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final App item) {
        appList.add(item);
        sectionHeader.add(appList.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? HEADER_ITEM : APP_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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

        MenuViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new MenuViewHolder();
            switch (rowType) {
                case APP_ITEM:
                    convertView = LayoutInflater.from(context).inflate(R.layout.menu_item, null);
                    holder.label = (TextView) convertView.findViewById(R.id.menu_item_label);


                    TextView addToHomeButton = (TextView) convertView.findViewById(R.id.menu_item_addToHome_button);
                    TextView uninstallButton = (TextView) convertView.findViewById(R.id.menu_item_uninstall_button);
                    final App app = (App) this.getItem(position);

                    convertView.setOnClickListener(new Menu_OnItemClickListener(context, app));


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
                            Intent intent = new Intent(Intent.ACTION_DELETE);
                            intent.setData(Uri.parse("package:" + appList.get(position).getPackageName()));
                            context.startActivity(intent);
                        }
                    });
                    break;
                case HEADER_ITEM:
                    convertView = LayoutInflater.from(context).inflate(R.layout.menu_header, null);
                    holder.label = (TextView) convertView.findViewById(R.id.menu_header_label);

                    break;
            }
            convertView.setTag(holder);
        }else {
            holder = (MenuViewHolder) convertView.getTag();
        }
        holder.label.setText(appList.get(position).getLabel());

        return convertView;


    }

    public static class MenuViewHolder {

        public TextView label;
        public TextView notification;

    }


}
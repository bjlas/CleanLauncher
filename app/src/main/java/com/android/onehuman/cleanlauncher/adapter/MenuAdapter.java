package com.android.onehuman.cleanlauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.events.Menu_App_OnItemClickListener;
import com.android.onehuman.cleanlauncher.events.Menu_Header_OnItemClickListener;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.Header;
import com.android.onehuman.cleanlauncher.persistence.DBController;
import com.android.onehuman.cleanlauncher.viewHolders.MenuAppViewHolder;
import com.android.onehuman.cleanlauncher.viewHolders.HeaderViewHolder;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter {

    private Context context;
    private final List<RowType> appList;
    private DBController dbController;

    public MenuAdapter(Context c, List<RowType> al) {
        this.context=c;
        this.appList = al;
        dbController=new DBController(c);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RowType.MENU_APP:
                View appView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
                return new MenuAppViewHolder(appView);

            case RowType.MENU_HEADER:
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
                return new HeaderViewHolder(headerView);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (appList.get(position) instanceof App) {
            return RowType.MENU_APP;
        }

        if (appList.get(position) instanceof Header) {
            return RowType.MENU_HEADER;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        RowType object = appList.get(position);
        if (object != null) {
            switch (getItemViewType(position)) {
                case RowType.MENU_APP:
                    final App app = (App) object;
                    MenuAppViewHolder appHolder = (MenuAppViewHolder) holder;
                    appHolder.label.setText(app.getLabel());
                    appHolder.label.setOnClickListener(new Menu_App_OnItemClickListener(context, app));

                    appHolder.addToHomeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(dbController.insert(app.getLabel(), app.getName(), app.getPackageName())){
                                Toast.makeText(context, context.getString(R.string.app_added_to_home)+app.getLabel(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.app_already_exist_at_home)+app.getLabel(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    appHolder.uninstallButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DELETE);
                            intent.setData(Uri.parse("package:" + app.getPackageName()));
                            context.startActivity(intent);
                        }
                    });

                    break;
                case RowType.MENU_HEADER:
                    Header header = (Header) object;
                    HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
                    headerHolder.label.setText(header.getLabel());
                    headerHolder.label.setOnClickListener(new Menu_Header_OnItemClickListener(context));
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return appList.size();
    }


}

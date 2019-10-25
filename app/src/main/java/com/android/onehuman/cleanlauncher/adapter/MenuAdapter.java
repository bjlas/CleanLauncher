package com.android.onehuman.cleanlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.events.App_OnItemClickListener;
import com.android.onehuman.cleanlauncher.events.Notification_OnItemClickListener;
import com.android.onehuman.cleanlauncher.events.AddToHome_OnItemClickListener;
import com.android.onehuman.cleanlauncher.events.Menu_Header_OnItemClickListener;
import com.android.onehuman.cleanlauncher.events.Uninstall_OnItemClickListener;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.HomeApp;
import com.android.onehuman.cleanlauncher.model.Header;
import com.android.onehuman.cleanlauncher.model.MenuApp;
import com.android.onehuman.cleanlauncher.model.Notification;
import com.android.onehuman.cleanlauncher.persistence.DBController;
import com.android.onehuman.cleanlauncher.viewHolders.MenuAppViewHolder;
import com.android.onehuman.cleanlauncher.viewHolders.HeaderViewHolder;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter {

    private Context context;
    private final List<RowType> appList;

    public MenuAdapter(Context c, List<RowType> al) {
        this.context=c;
        this.appList = al;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RowType.MENU_APP:
                View appView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_app, parent, false);
                return new MenuAppViewHolder(appView);
            case RowType.MENU_HEADER:
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_header, parent, false);
                return new HeaderViewHolder(headerView);
            case RowType.MENU_NOTIFICATION:
                View notificationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_notification, parent, false);
                return new MenuAppViewHolder(notificationView);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (appList.get(position) instanceof Header) {
            return RowType.MENU_HEADER;
        }

        if (appList.get(position) instanceof Notification) {
            return RowType.MENU_NOTIFICATION;
        }

        if (appList.get(position) instanceof MenuApp) {
            return RowType.MENU_APP;
        }

        return -1;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        RowType object = appList.get(position);
        if (object != null) {
            switch (getItemViewType(position)) {
                case RowType.MENU_APP:
                    final MenuApp app = (MenuApp) object;
                    MenuAppViewHolder appHolder = (MenuAppViewHolder) holder;
                    appHolder.label.setText(app.getLabel());
                    appHolder.label.setOnClickListener(new App_OnItemClickListener(context, app));
                    appHolder.addToHomeButton.setOnClickListener(new AddToHome_OnItemClickListener(context, app));
                    appHolder.uninstallButton.setOnClickListener(new Uninstall_OnItemClickListener(context, app));
                    break;

                case RowType.MENU_HEADER:
                    Header header = (Header) object;
                    HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
                    headerHolder.label.setText(header.getLabel());
                    headerHolder.label.setOnClickListener(new Menu_Header_OnItemClickListener(context,appList));
                    break;

                case RowType.MENU_NOTIFICATION:
                    Notification notification = (Notification) object;
                    MenuAppViewHolder notificationHolder = (MenuAppViewHolder) holder;
                    notificationHolder.label.setText(notification.getLabel());
                    notificationHolder.label.setOnClickListener(new Notification_OnItemClickListener(context,notification));
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return appList.size();
    }


}

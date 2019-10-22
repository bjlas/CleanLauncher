package com.android.onehuman.cleanlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.events.Home_Notification_OnItemClickListener;
import com.android.onehuman.cleanlauncher.interfaces.ItemTouchHelperAdapter;
import com.android.onehuman.cleanlauncher.interfaces.OnHomeClickListener;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.LauncherApp;
import com.android.onehuman.cleanlauncher.model.Notification;
import com.android.onehuman.cleanlauncher.persistence.DBController;
import com.android.onehuman.cleanlauncher.viewHolders.HomeAppViewHolder;
import com.android.onehuman.cleanlauncher.viewHolders.HomeNotificationViewHolder;


import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {



    private Context context;
    private ArrayList<RowType> appList;
    private ItemTouchHelper itemTouchHelper;
    private DBController dbController;
    private OnHomeClickListener onHomeClickListener;



    public HomeAdapter(Context c, ArrayList<RowType> al, OnHomeClickListener onHomeClickListener) {
        this.context=c;
        this.appList = al;
        this.onHomeClickListener = onHomeClickListener;
        dbController = new DBController(context);
    }



    public void updateAppList(ArrayList<RowType> al) {
        this.appList = al;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (appList.get(position) instanceof LauncherApp) {
            return RowType.HOME_APP;
        }

        if (appList.get(position) instanceof Notification) {
            return RowType.HOME_NOTIFICATION;
        }
        return -1;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RowType.HOME_APP:
                View appView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
                return new HomeAppViewHolder(appView, itemTouchHelper, onHomeClickListener);

            case RowType.HOME_NOTIFICATION:
                View notificationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_notification_item, parent, false);
                return new HomeNotificationViewHolder(notificationView);
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        RowType object = appList.get(position);
        if (object != null) {
            switch (getItemViewType(position)) {
                case RowType.HOME_APP:
                    final LauncherApp app = (LauncherApp) object;
                    HomeAppViewHolder appHolder = (HomeAppViewHolder) holder;
                    appHolder.label.setText(app.getLabel());

//                    appHolder.label.setOnClickListener(new Home_App_OnItemClickListener(context, app));


                    break;
                case RowType.HOME_NOTIFICATION:
                    final Notification notification = (Notification) object;
                    HomeNotificationViewHolder notificationViewHolder = (HomeNotificationViewHolder) holder;
                    notificationViewHolder.label.setText("NOTIFICIACION TEST");
                    notificationViewHolder.label.setOnClickListener(new Home_Notification_OnItemClickListener(context, notification));

                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        if(getItemViewType(fromPosition) == RowType.HOME_APP & getItemViewType(toPosition) == RowType.HOME_APP) {
            LauncherApp app_origin = (LauncherApp)appList.get(fromPosition);
            LauncherApp app_dest = (LauncherApp) appList.get(toPosition);

            app_origin.setPosition(toPosition);
            app_dest.setPosition(fromPosition);

            dbController.updateApp(app_origin);
            dbController.updateApp(app_dest);

            appList.remove(app_origin);
            appList.add(toPosition, app_dest);

            notifyItemMoved(fromPosition, toPosition);
        }

    }

    @Override
    public void onItemSwiped(int position) {
        if(getItemViewType(position) == RowType.HOME_APP) {
            dbController.deleteApp(((LauncherApp) appList.get(position)).getPackageName());
            appList.remove(position);

            for (int index=0; index < appList.size(); index++) {
                LauncherApp app = (LauncherApp)appList.get(index);
                app.setPosition(index);
                dbController.updateApp(app);
            }
            notifyItemRemoved(position);
        }
    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.itemTouchHelper = touchHelper;
    }




}









package com.android.onehuman.cleanlauncher.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.viewHolders.HomeNotificationViewHolder;
import java.util.List;

public class HomeNotificationAdapter extends RecyclerView.Adapter<HomeNotificationViewHolder> {

    private List<App> notificationList;
    public HomeNotificationAdapter(List<App> horizontalList) {
        this.notificationList = horizontalList;
    }

    @Override
    public HomeNotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_notification, parent, false);
        return new HomeNotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeNotificationViewHolder holder, final int position) {
        holder.number.setText("("+notificationList.get(position).getNotification()+")");
        holder.label.setText(notificationList.get(position).getLabel());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

}
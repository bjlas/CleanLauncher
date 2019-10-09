package com.android.onehuman.cleanlauncher.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.Notification;

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    public TextView label;
    public TextView date;
    public TextView time;
    public TextView text;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        label = (TextView) itemView.findViewById(R.id.home_notification_label);
        date = (TextView) itemView.findViewById(R.id.home_notification_date);
        time = (TextView) itemView.findViewById(R.id.home_notification_time);
        text = (TextView) itemView.findViewById(R.id.home_notification_text);
    }

}

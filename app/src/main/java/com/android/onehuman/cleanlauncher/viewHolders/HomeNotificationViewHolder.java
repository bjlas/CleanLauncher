package com.android.onehuman.cleanlauncher.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;

public class HomeNotificationViewHolder extends RecyclerView.ViewHolder {

    public TextView number;
    public TextView label;

    public HomeNotificationViewHolder(View itemView) {
        super(itemView);
        number = (TextView) itemView.findViewById(R.id.home_notification_number);
        label = (TextView) itemView.findViewById(R.id.home_notification_label);
    }

}

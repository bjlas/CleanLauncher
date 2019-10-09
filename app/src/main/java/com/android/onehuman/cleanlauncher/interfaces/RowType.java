package com.android.onehuman.cleanlauncher.interfaces;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public interface RowType {
    int APP_TYPE = 0;
    int NOTIFICATION_TYPE = 1;

    int getItemViewType();
    void onBindViewHolder(RecyclerView.ViewHolder viewHolder);
}

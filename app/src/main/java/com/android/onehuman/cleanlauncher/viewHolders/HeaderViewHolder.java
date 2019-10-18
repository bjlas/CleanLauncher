package com.android.onehuman.cleanlauncher.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    public TextView label;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        label = (TextView) itemView.findViewById(R.id.menu_header_label);
    }

}

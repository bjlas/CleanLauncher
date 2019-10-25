package com.android.onehuman.cleanlauncher.viewHolders;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.onehuman.cleanlauncher.R;

public class MenuAppViewHolder extends RecyclerView.ViewHolder {

    public TextView label;
    public TextView addToHomeButton;
    public TextView uninstallButton;

    public MenuAppViewHolder(View itemView) {
        super(itemView);
        label = itemView.findViewById(R.id.menu_item_notification_label);
        addToHomeButton = (TextView) itemView.findViewById(R.id.menu_item_addToHome_button);
        uninstallButton = (TextView) itemView.findViewById(R.id.menu_item_notification_alert);
    }

}

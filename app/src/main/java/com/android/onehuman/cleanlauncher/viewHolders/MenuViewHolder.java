package com.android.onehuman.cleanlauncher.viewHolders;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.onehuman.cleanlauncher.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    public TextView label;
    public TextView addToHomeButton;
    public TextView uninstallButton;

    public MenuViewHolder(View itemView) {
        super(itemView);
        label = itemView.findViewById(R.id.menu_item_label);
        addToHomeButton = (TextView) itemView.findViewById(R.id.menu_item_addToHome_button);
        uninstallButton = (TextView) itemView.findViewById(R.id.menu_item_uninstall_button);
    }

}

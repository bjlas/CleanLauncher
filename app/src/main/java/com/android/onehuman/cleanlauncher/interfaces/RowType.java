package com.android.onehuman.cleanlauncher.interfaces;

import android.os.Parcelable;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public interface RowType {
    int HOME_APP = 0;
    int HOME_NOTIFICATION = 1;
    int MENU_APP = 2;
    int MENU_NOTIFICATION = 3;
    int MENU_HEADER = 4;
}

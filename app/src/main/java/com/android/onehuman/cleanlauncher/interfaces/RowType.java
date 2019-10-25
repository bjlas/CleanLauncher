package com.android.onehuman.cleanlauncher.interfaces;

public interface RowType {
    int HOME_APP = 0;
    int HOME_NOTIFICATION = 1;
    int MENU_APP = 2;
    int MENU_NOTIFICATION = 3;
    int MENU_HEADER = 4;

    int hashCode();
    boolean equals(Object obj);
}

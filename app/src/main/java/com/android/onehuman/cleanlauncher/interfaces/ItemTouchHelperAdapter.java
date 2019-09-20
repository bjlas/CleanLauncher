package com.android.onehuman.cleanlauncher.interfaces;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemSwiped(int position);
}

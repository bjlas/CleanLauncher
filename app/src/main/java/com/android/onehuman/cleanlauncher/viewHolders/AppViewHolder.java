package com.android.onehuman.cleanlauncher.viewHolders;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.interfaces.OnHomeClickListener;
import com.android.onehuman.cleanlauncher.model.App;

public class AppViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener
{

        public TextView label;
        public TextView notification;


        OnHomeClickListener onHomeClickListener;
        GestureDetector gestureDetector;
        private ItemTouchHelper itemTouchHelper;

        public AppViewHolder(View itemView, OnHomeClickListener ocl, ItemTouchHelper ith) {
            super(itemView);
            label = itemView.findViewById(R.id.home_item_label);
            notification = itemView.findViewById(R.id.home_item_notification);
            itemTouchHelper=ith;
            onHomeClickListener = ocl;
            gestureDetector = new GestureDetector(itemView.getContext(), this);
            itemView.setOnTouchListener(this);
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onHomeClickListener.onHomeClick(getAdapterPosition());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            itemTouchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
}

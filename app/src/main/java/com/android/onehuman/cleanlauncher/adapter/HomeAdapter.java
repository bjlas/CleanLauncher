package com.android.onehuman.cleanlauncher.adapter;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.interfaces.ItemTouchHelperAdapter;
import com.android.onehuman.cleanlauncher.interfaces.OnHomeClickListener;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.persistence.DBController;


import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<App> appList;
    private OnHomeClickListener onHomeClickListener;
    private ItemTouchHelper itemTouchHelper;
    private DBController dbController;

    public HomeAdapter(Context context, ArrayList<App> al, OnHomeClickListener onHomeClickListener) {
        this.appList = al;
        this.onHomeClickListener = onHomeClickListener;
        dbController = new DBController(context);
    }

    public void updateAppList(ArrayList<App> al) {
        this.appList = al;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new HomeViewHolder(view, onHomeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
            holder.label.setText(appList.get(position).getLabel());

            if (appList.get(position).getNotification()) {
                holder.notification.setVisibility(View.VISIBLE);
            } else {
                holder.notification.setVisibility(View.INVISIBLE);
            }
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        App app = appList.get(fromPosition);
        appList.remove(app);
        appList.add(toPosition, app);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        dbController.delete(appList.get(position).getPackageName());
        appList.remove(position);
        notifyItemRemoved(position);
    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.itemTouchHelper = touchHelper;
    }



    public class HomeViewHolder extends RecyclerView.ViewHolder implements
            View.OnTouchListener,
            GestureDetector.OnGestureListener
    {

        public TextView label;
        public TextView notification;

        OnHomeClickListener onHomeClickListener;
        GestureDetector gestureDetector;

        public HomeViewHolder(View itemView, OnHomeClickListener ocl) {
            super(itemView);
            label = itemView.findViewById(R.id.home_item_label);
            notification = itemView.findViewById(R.id.home_item_notification);

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
}









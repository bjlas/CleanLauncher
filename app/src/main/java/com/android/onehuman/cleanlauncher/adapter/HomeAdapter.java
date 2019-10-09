package com.android.onehuman.cleanlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.interfaces.ItemTouchHelperAdapter;
import com.android.onehuman.cleanlauncher.interfaces.OnHomeClickListener;
import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.persistence.DBController;
import com.android.onehuman.cleanlauncher.viewHolders.AppViewHolder;
import com.android.onehuman.cleanlauncher.viewHolders.NotificationViewHolder;


import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {



    private Context context;
    private ArrayList<RowType> appList;
    private OnHomeClickListener onHomeClickListener;
    private ItemTouchHelper itemTouchHelper;
    private DBController dbController;


    public HomeAdapter(Context context, ArrayList<RowType> al, OnHomeClickListener onHomeClickListener) {
        this.context=context;
        this.appList = al;
        this.onHomeClickListener = onHomeClickListener;
        dbController = new DBController(context);
    }

    public void updateAppList(ArrayList<RowType> al) {
        this.appList = al;
        notifyDataSetChanged();
    }

    public int getItemViewType(int position) {
        return appList.get(position).getItemViewType();
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RowType.APP_TYPE:
                View buttonTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
                return new AppViewHolder(buttonTypeView, onHomeClickListener, itemTouchHelper);

            case RowType.NOTIFICATION_TYPE:
                View textTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
                return new NotificationViewHolder(textTypeView);


            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        appList.get(position).onBindViewHolder(holder);
    }

/*    public void showDetails(App chat){

        holder.label.setText(appList.get(position).getLabel());

        if (appList.get(position).getNotification() > 0) {
            holder.label.setText(appList.get(position).getLabel());
            holder.label.setTextColor(context.getResources().getColor(R.color.color_home_notification_on_text));
            holder.label.setBackgroundColor(context.getResources().getColor(R.color.color_home_notification_on_background));
            holder.notification.setText("("+appList.get(position).getNotification()+")");
            holder.notification.setVisibility(View.VISIBLE);
        } else {
            holder.label.setTextColor(context.getResources().getColor(R.color.color_home_notification_off_text));
            holder.label.setBackgroundColor(Color.TRANSPARENT);
            holder.notification.setText("");
            holder.notification.setVisibility(View.INVISIBLE);
        }
    }*/




    @Override
    public int getItemCount() {
        return appList.size();
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        App app_origin = (App )appList.get(fromPosition);
        App app_dest = (App) appList.get(toPosition);

        app_origin.setPosition(toPosition);
        app_dest.setPosition(fromPosition);

        dbController.updatePosition(app_origin, toPosition);
        dbController.updatePosition(app_dest, fromPosition);

        appList.remove(app_origin);
        appList.add(toPosition, app_origin);

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        dbController.delete(((App)appList.get(position)).getPackageName());
        appList.remove(position);
        //dbController.updateAllPositions(appList);
        notifyItemRemoved(position);
    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.itemTouchHelper = touchHelper;
    }




}









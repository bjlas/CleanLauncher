package com.android.onehuman.cleanlauncher.model;

import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.viewHolders.MenuViewHolder;
import com.android.onehuman.cleanlauncher.viewHolders.NotificationViewHolder;

public class Notification implements RowType {

    private String label;
    private String name;
    private String packageName;
    private String date;
    private String time;
    private String text;

    public Notification(String label, String name, String packageName, String date, String time, String text) {
        this.label=label;
        this.date=date;
        this.time=time;
        this.text=text;
    }


    public String getLabel() { return label; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getText() { return text; }

    @Override
    public int getItemViewType() {
        return RowType.NOTIFICATION_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder) {
        NotificationViewHolder textViewHolder = (NotificationViewHolder) viewHolder;
        textViewHolder.label.setText(label);
        textViewHolder.date.setText(date);
        textViewHolder.time.setText(time);
        textViewHolder.text.setText(text);
    }


}

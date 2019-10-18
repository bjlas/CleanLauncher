package com.android.onehuman.cleanlauncher.model;

import com.android.onehuman.cleanlauncher.interfaces.RowType;

public class Notification implements RowType {

    private String label;
    private String name;
    private String packageName;
    private int position;
    private int notification;


    public Notification(String label, String name, String packageName, int pos, int notif) {
        this.label=label;
        this.name=name;
        this.packageName=packageName;
        this.position=pos;
        this.notification=notif;
    }

    public String getLabel() { return label; }
    public String getName() { return name; }
    public String getPackageName() { return packageName; }
    public int getNotification() { return notification; }
    public void setNotification(int noti) { this.notification=noti; }


}

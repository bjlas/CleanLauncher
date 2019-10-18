package com.android.onehuman.cleanlauncher.model;

import com.android.onehuman.cleanlauncher.interfaces.RowType;

public class App implements RowType, Comparable<App> {

    private String label;
    private String name;
    private String packageName;
    private int position;
    private int notification;

    public App(String l, String n, String pn) {
        this.label=l;
        this.name=n;
        this.packageName=pn;
        notification=0;
    }

    public App(String l, String n, String pn, int p, int noti) {
        this.label=l;
        this.name=n;
        this.packageName=pn;
        this.position=p;
        this.notification=noti;
    }


    public String getLabel() { return label; }
    public String getName() { return name; }
    public String getPackageName() { return packageName; }
    public int getPosition() { return position; }
    public int getNotification() { return notification; }
    public void setPosition(int position) { this.position = position; }


    @Override
    public int compareTo(App app) {
        return getLabel().toString()
                .compareTo(app.getLabel()
                        .toString());
    }

}

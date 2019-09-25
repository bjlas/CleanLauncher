package com.android.onehuman.cleanlauncher.model;

import java.io.Serializable;
import java.util.UUID;

public class App implements Serializable, Comparable<App> {

    private String label;
    private String name;
    private String packageName;
    private int position;
    private boolean notification;


    public App(String l, String n, String pn) {
        this.label=l;
        this.name=n;
        this.packageName=pn;
        notification=false;
    }


    public String getLabel() { return label; }
    public String getName() { return name; }
    public String getPackageName() { return packageName; }
    public boolean getNotification() { return notification; }
    public void setNotification(boolean notification) { this.notification = notification; }


    @Override
    public boolean equals(Object obj) {
        return (this.packageName.equals(((App) obj).packageName));
    }

    @Override
    public int compareTo(App other) {
        return label.compareTo(other.label);
    }



}

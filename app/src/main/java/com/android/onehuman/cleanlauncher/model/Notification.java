package com.android.onehuman.cleanlauncher.model;

public class Notification extends App{

    private int notification;

    public Notification(String l, String n, String pn, int notif) {
        super(l,n,pn);
        this.notification=notif;
    }

    public int getNotification() { return notification; }
    public void setNotification(int noti) { this.notification=noti; }

}

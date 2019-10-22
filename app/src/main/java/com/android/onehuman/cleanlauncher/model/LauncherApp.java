package com.android.onehuman.cleanlauncher.model;

public class LauncherApp extends App {


    private int position;

    public LauncherApp(String l, String n, String pn) {
        super(l,n,pn);
    }

    public LauncherApp(String l, String n, String pn, int p) {
        super(l,n,pn);
        this.position=p;
    }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }



}

package com.android.onehuman.cleanlauncher.model;

public class HomeApp extends App {

    private int position;

    public HomeApp(String l, String n, String pn, int p) {
        super(l,n,pn);
        this.position=p;
    }

    public int getPosition() { return position; }
    public void setPosition(int pos) { this.position=pos; }


}

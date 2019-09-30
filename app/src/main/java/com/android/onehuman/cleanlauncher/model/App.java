package com.android.onehuman.cleanlauncher.model;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.adapter.MenuAdapter;

import java.io.Serializable;
import java.util.UUID;

public class App implements Comparable<App> {

    private String label;
    private String name;
    private String packageName;
    private int position;
    private boolean notification;

    public App(String l) {
        this.label=l;
    }

    public App(String l, String n, String pn) {
        this.label=l;
        this.name=n;
        this.packageName=pn;
        notification=false;
    }

    public App(String l, String n, String pn, int p) {
        this.label=l;
        this.name=n;
        this.packageName=pn;
        this.position=p;
        notification=false;
    }


    public String getLabel() { return label; }
    public String getName() { return name; }
    public String getPackageName() { return packageName; }
    public int getPosition() { return position; }

    public boolean getNotification() { return notification; }
    public void setNotification(boolean notification) { this.notification = notification; }
    public void setPosition(int position) { this.position = position; }


    @Override
    public int compareTo(App app) {
        return getLabel().toString()
                .compareTo(app.getLabel()
                        .toString());
    }

}

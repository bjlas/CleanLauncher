package com.android.onehuman.cleanlauncher.model;

import java.io.Serializable;
import java.util.UUID;

public class App implements Serializable, Comparable<App> {

    public long id;
    public String label;
    public String name;
    public String packageName;


    public App(String l, String n, String pn) {
        this.label=l;
        this.name=n;
        this.packageName=pn;
    }

    public App(long id, String l, String n, String pn) {
        id = id;
        this.label=l;
        this.name=n;
        this.packageName=pn;
    }

    public long getId() { return id; }
    public String getLabel() { return label; }
    public String getName() { return name; }
    public String getPackageName() { return packageName; }

    @Override
    public boolean equals(Object obj) {
        return (this.label.equals(((App) obj).label)
                && this.name.equals(((App) obj).name)
                && this.packageName.equals(((App) obj).packageName));
    }

    @Override
    public int compareTo(App other) {
        return label.compareTo(other.label);
    }

}

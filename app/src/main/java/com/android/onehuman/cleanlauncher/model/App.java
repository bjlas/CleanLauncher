package com.android.onehuman.cleanlauncher.model;

import com.android.onehuman.cleanlauncher.interfaces.RowType;

public class App implements RowType, Comparable<LauncherApp> {

    private String label;
    private String name;
    private String packageName;

    public App(String l, String n, String pn) {
        this.label=l;
        this.name=n;
        this.packageName=pn;
    }

    public String getLabel() { return label; }
    public String getName() { return name; }
    public String getPackageName() { return packageName; }

    @Override
    public int compareTo(LauncherApp app) {
        return getLabel().toString()
                .compareTo(app.getLabel()
                        .toString());
    }

}

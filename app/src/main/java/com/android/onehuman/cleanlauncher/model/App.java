package com.android.onehuman.cleanlauncher.model;

import com.android.onehuman.cleanlauncher.interfaces.RowType;

public class App implements RowType {

    private String packageName;
    private String label;
    private String name;

    public App(String pn, String n, String l ) {
        this.packageName=pn;
        this.name=n;
        this.label=l;

    }
    public String getPackageName() { return packageName; }
    public String getName() { return name; }
    public String getLabel() { return label; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        App other = (App) obj;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        return true;
    }

}

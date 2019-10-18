package com.android.onehuman.cleanlauncher.model;

import com.android.onehuman.cleanlauncher.interfaces.RowType;

public class Header implements RowType {

    private String label;

    public Header(String label) {
        this.label=label;
    }
    public String getLabel() { return label; }


}

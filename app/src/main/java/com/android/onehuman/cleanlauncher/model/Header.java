package com.android.onehuman.cleanlauncher.model;

import com.android.onehuman.cleanlauncher.interfaces.RowType;

public class Header implements RowType {

    private String label;

    public Header(String label) {
        this.label=label;
    }

    public String getLabel() { return label; }
    public void setLabel(String l) { this.label=l; }

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
        Header other = (Header) obj;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        return true;
    }

}

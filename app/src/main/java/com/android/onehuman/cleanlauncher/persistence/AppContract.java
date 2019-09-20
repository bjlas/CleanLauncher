package com.android.onehuman.cleanlauncher.persistence;

import android.provider.BaseColumns;

public class AppContract {

    private AppContract() {}

    public static class AppEntry implements BaseColumns {

        public static final String TABLE_NAME ="apps";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LABEL = "label";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PACKAGENAME = "packageName";
    }

}
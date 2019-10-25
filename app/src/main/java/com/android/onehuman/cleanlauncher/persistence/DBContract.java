package com.android.onehuman.cleanlauncher.persistence;

import android.provider.BaseColumns;

public class DBContract {

    private DBContract() {}

    public static class AppEntry implements BaseColumns {

        public static final String TABLE_NAME ="apps";

        public static final String COLUMN_LABEL = "label";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PACKAGENAME = "packageName";


        public static String createTablesql =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COLUMN_PACKAGENAME + " TEXT PRIMARY KEY,"
                        + COLUMN_LABEL + " TEXT NOT NULL,"
                        + COLUMN_NAME + " TEXT NOT NULL,"
                        + "UNIQUE (" + COLUMN_PACKAGENAME + "))";

    }

    public static class HomeAppEntry implements BaseColumns {

        public static final String TABLE_NAME ="HomeApps";
        public static final String COLUMN_PACKAGENAME = "packageName";
        public static final String COLUMN_POSITION = "position";

        public static String createTablesql =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COLUMN_PACKAGENAME + " TEXT PRIMARY KEY,"
                        + COLUMN_POSITION + " INTEGER NOT NULL,"
                        + "UNIQUE (" + COLUMN_PACKAGENAME + "),"
                        + " FOREIGN KEY ("+COLUMN_PACKAGENAME+") REFERENCES "+AppEntry.TABLE_NAME+"("+AppEntry.COLUMN_PACKAGENAME+"));";
    }

    public static class NotificationEntry implements BaseColumns {

        public static final String TABLE_NAME ="notifications";
        public static final String COLUMN_PACKAGENAME = "packageName";
        public static final String COLUMN_NOTIFICATION = "notification";

        public static String createTablesql =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COLUMN_PACKAGENAME + " TEXT PRIMARY KEY,"
                        + COLUMN_NOTIFICATION + " INTEGER NOT NULL,"
                        + "UNIQUE (" + COLUMN_PACKAGENAME + "),"
                        + " FOREIGN KEY ("+COLUMN_PACKAGENAME+") REFERENCES "+AppEntry.TABLE_NAME+"("+AppEntry.COLUMN_PACKAGENAME+"));";


    }


}
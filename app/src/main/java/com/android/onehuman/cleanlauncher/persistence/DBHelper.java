package com.android.onehuman.cleanlauncher.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper dbHelper;
    private static final int DATABASE_VERSION = 11;
    private static final String DATABASE_NAME = "CleanLauncherDB.db";

    public static synchronized DBHelper getInstance(Context c) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(c.getApplicationContext());
        }
        return dbHelper;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.AppEntry.createTablesql);
        db.execSQL(DBContract.NotificationEntry.createTablesql);
        db.execSQL(DBContract.HomeAppEntry.createTablesql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.AppEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.NotificationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.HomeAppEntry.TABLE_NAME);

        onCreate(db);
    }

}
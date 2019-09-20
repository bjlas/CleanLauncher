package com.android.onehuman.cleanlauncher.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CleanLauncherDB.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AppContract.AppEntry.TABLE_NAME + " ("
                + AppContract.AppEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AppContract.AppEntry.COLUMN_LABEL + " TEXT NOT NULL,"
                + AppContract.AppEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + AppContract.AppEntry.COLUMN_PACKAGENAME + " TEXT NOT NULL,"
                + "UNIQUE (" + AppContract.AppEntry.COLUMN_ID + "))");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AppContract.AppEntry.TABLE_NAME);
        onCreate(db);
    }

}
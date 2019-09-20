package com.android.onehuman.cleanlauncher.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.onehuman.cleanlauncher.model.App;

import java.util.ArrayList;


public class DBController {

    private DBHelper dbHelper;

    public DBController(Context context)
    {
        dbHelper = new DBHelper(context);
    }


    public long insert(String label, String name, String packagename) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppContract.AppEntry.COLUMN_LABEL, label);
        values.put(AppContract.AppEntry.COLUMN_NAME, name);
        values.put(AppContract.AppEntry.COLUMN_PACKAGENAME, packagename);

        return db.insert(AppContract.AppEntry.TABLE_NAME, null, values);

    }

    public ArrayList<App> getAll() {
        ArrayList<App> appList = new ArrayList<>();
        SQLiteDatabase baseDeDatos = dbHelper.getReadableDatabase();
        String[] columnasAConsultar = {AppContract.AppEntry.COLUMN_ID, AppContract.AppEntry.COLUMN_LABEL, AppContract.AppEntry.COLUMN_NAME, AppContract.AppEntry.COLUMN_PACKAGENAME};
        Cursor cursor = baseDeDatos.query(AppContract.AppEntry.TABLE_NAME, columnasAConsultar, null, null, null, null, null);

        if (cursor == null) {
            return appList;
        }
        if (!cursor.moveToFirst()) return appList;

        do {

            App app = new App(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));

            appList.add(app);
        } while (cursor.moveToNext());

        cursor.close();
        return appList;
    }




}

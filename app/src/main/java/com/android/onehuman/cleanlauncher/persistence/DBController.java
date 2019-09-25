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
        dbHelper = DBHelper.getInstance(context);
    }


    public boolean insert(String label, String name, String packagename) {

        boolean result=false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {AppContract.AppEntry.COLUMN_PACKAGENAME};
        String whereClause = AppContract.AppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {packagename};

        Cursor cursor = db.query(AppContract.AppEntry.TABLE_NAME, columns, whereClause, args, null, null, null);

        if(cursor.getCount()>0){
            result=false;
        }else{
            ContentValues values = new ContentValues();
            values.put(AppContract.AppEntry.COLUMN_LABEL, label);
            values.put(AppContract.AppEntry.COLUMN_NAME, name);
            values.put(AppContract.AppEntry.COLUMN_PACKAGENAME, packagename);

            db.insertWithOnConflict(AppContract.AppEntry.TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
            result = true;
        }

        cursor.close();
        return result;
    }

    public int delete(String packageName) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = AppContract.AppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(packageName)};
        return db.delete(AppContract.AppEntry.TABLE_NAME, whereClause, args);
    }

    public ArrayList<App> getAll() {
        ArrayList<App> appList = new ArrayList<>();
        SQLiteDatabase baseDeDatos = dbHelper.getReadableDatabase();
        String[] columnasAConsultar = {AppContract.AppEntry.COLUMN_LABEL, AppContract.AppEntry.COLUMN_NAME, AppContract.AppEntry.COLUMN_PACKAGENAME};
        Cursor cursor = baseDeDatos.query(AppContract.AppEntry.TABLE_NAME, columnasAConsultar, null, null, null, null, null);

        if (cursor == null) {
            return appList;
        }
        if (!cursor.moveToFirst()) return appList;

        do {

            App app = new App(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2));

            appList.add(app);
        } while (cursor.moveToNext());

        cursor.close();
        return appList;
    }

    public int update(App app) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valuesForUpdate = new ContentValues();
        valuesForUpdate.put(AppContract.AppEntry.COLUMN_LABEL, app.getLabel());
        valuesForUpdate.put(AppContract.AppEntry.COLUMN_NAME, app.getName());
        String whereClause = AppContract.AppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(app.getPackageName())};
        return db.update(AppContract.AppEntry.TABLE_NAME, valuesForUpdate, whereClause, args);
    }




}

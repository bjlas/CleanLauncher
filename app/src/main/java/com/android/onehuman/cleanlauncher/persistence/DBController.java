package com.android.onehuman.cleanlauncher.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.Notification;

import java.util.ArrayList;
import android.util.Log;


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
            //already exists in DB
            result=false;
        }else{
            //No exists in DB
            ContentValues values = new ContentValues();
            values.put(AppContract.AppEntry.COLUMN_LABEL, label);
            values.put(AppContract.AppEntry.COLUMN_NAME, name);
            values.put(AppContract.AppEntry.COLUMN_PACKAGENAME, packagename);
            values.put(AppContract.AppEntry.COLUMN_NOTIFICATION, 0);

            values.put(AppContract.AppEntry.COLUMN_POSITION, getTableSize()+1);


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


    public int updateNotification(String packageName, int notification) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valuesForUpdate = new ContentValues();
        valuesForUpdate.put(AppContract.AppEntry.COLUMN_NOTIFICATION, notification);
        String whereClause = AppContract.AppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(packageName)};
        return db.update(AppContract.AppEntry.TABLE_NAME, valuesForUpdate, whereClause, args);
    }

    public ArrayList<RowType> getAll() {
        ArrayList<RowType> appList = new ArrayList<>();
        SQLiteDatabase baseDeDatos = dbHelper.getReadableDatabase();
        String[] columnasAConsultar = {AppContract.AppEntry.COLUMN_LABEL, AppContract.AppEntry.COLUMN_NAME, AppContract.AppEntry.COLUMN_PACKAGENAME, AppContract.AppEntry.COLUMN_POSITION,  AppContract.AppEntry.COLUMN_NOTIFICATION};
        String orderby = AppContract.AppEntry.COLUMN_POSITION + " ASC";
        Cursor cursor = baseDeDatos.query(AppContract.AppEntry.TABLE_NAME, columnasAConsultar, null, null, null, null, orderby);

        if (cursor == null) {
            return appList;
        }
        if (!cursor.moveToFirst()) return appList;

        do {
            String label=cursor.getString(0);
            String name=cursor.getString(1);
            String packageName=cursor.getString(2);
            int position=cursor.getInt(3);
            int notification=cursor.getInt(4);

            if(notification==0) {
                App app = new App(label, name, packageName, position, notification);
                appList.add(app);
            } else {
                Notification noti = new Notification(label, name, packageName, position, notification);
                appList.add(noti);
            }

        } while (cursor.moveToNext());

        cursor.close();
        return appList;
    }

    public int updatePosition(App app, int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valuesForUpdate = new ContentValues();
        valuesForUpdate.put(AppContract.AppEntry.COLUMN_LABEL, app.getLabel());
        valuesForUpdate.put(AppContract.AppEntry.COLUMN_NAME, app.getName());
        valuesForUpdate.put(AppContract.AppEntry.COLUMN_POSITION, position);
        valuesForUpdate.put(AppContract.AppEntry.COLUMN_NOTIFICATION, app.getNotification());
        String whereClause = AppContract.AppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(app.getPackageName())};
        return db.update(AppContract.AppEntry.TABLE_NAME, valuesForUpdate, whereClause, args);
    }

    public void increaseNotificationValue(String packageName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "UPDATE "+ AppContract.AppEntry.TABLE_NAME +
                " SET "+AppContract.AppEntry.COLUMN_NOTIFICATION+" = "+AppContract.AppEntry.COLUMN_NOTIFICATION+"+1 " +
                "WHERE "+AppContract.AppEntry.COLUMN_PACKAGENAME+" = \""+packageName+"\"";

        db.execSQL(query);
        db.close();

    }

    public void clearNotifications(String packageName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "UPDATE "+ AppContract.AppEntry.TABLE_NAME +
                " SET "+AppContract.AppEntry.COLUMN_NOTIFICATION+" = 0 " +
                "WHERE "+AppContract.AppEntry.COLUMN_PACKAGENAME+" = \""+packageName+"\"";

        db.execSQL(query);
        db.close();
    }


    public void updateAllPositions(ArrayList<App> appList) {

        for (int index=0; index < appList.size(); index++) {
            updatePosition(appList.get(index), index);
        }
    }

    public long getTableSize() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, AppContract.AppEntry.TABLE_NAME);
        return count;
    }




}

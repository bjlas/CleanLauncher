package com.android.onehuman.cleanlauncher.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.LauncherApp;
import com.android.onehuman.cleanlauncher.model.Notification;

import java.util.ArrayList;


public class DBController {

    private DBHelper dbHelper;

    public DBController(Context context)
    {
        dbHelper = DBHelper.getInstance(context);
    }


    public boolean insertApp(String label, String name, String packagename) {

        boolean result=false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DBContract.AppEntry.COLUMN_PACKAGENAME};
        String whereClause = DBContract.AppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {packagename};

        Cursor cursor = db.query(DBContract.AppEntry.TABLE_NAME, columns, whereClause, args, null, null, null);

        if(cursor.getCount()>0){
            //already exists in DB
            result=false;
        }else{
            //No exists in DB
            ContentValues values = new ContentValues();
            values.put(DBContract.AppEntry.COLUMN_LABEL, label);
            values.put(DBContract.AppEntry.COLUMN_NAME, name);
            values.put(DBContract.AppEntry.COLUMN_PACKAGENAME, packagename);
            values.put(DBContract.AppEntry.COLUMN_POSITION, getAppTableSize()+1);
            db.insertWithOnConflict(DBContract.AppEntry.TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
            result = true;
        }

        cursor.close();
        return result;
    }
    public long getAppTableSize() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, DBContract.AppEntry.TABLE_NAME);
        return count;
    }


    public boolean checkNotifications(String packageName) {

        boolean result=false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DBContract.NotificationEntry.COLUMN_NOTIFICATION};
        String whereClause = DBContract.NotificationEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {packageName};

        Cursor cursor = db.query(DBContract.NotificationEntry.TABLE_NAME, columns, whereClause, args, null, null, null);

        if(cursor.getCount()>0){
            result=true;
        }

        cursor.close();
        return result;
    }

    public ArrayList<RowType> getAllApps() {

        ArrayList<RowType> appList = new ArrayList<>();

        String selectQuery = "SELECT " +
                "app."+DBContract.AppEntry.COLUMN_LABEL+", " +
                "app."+DBContract.AppEntry.COLUMN_NAME+", " +
                "app."+DBContract.AppEntry.COLUMN_PACKAGENAME+", " +
                "app."+DBContract.AppEntry.COLUMN_POSITION+", " +
                "noti."+DBContract.NotificationEntry.COLUMN_NOTIFICATION+" " +
                "FROM " +
                ""+DBContract.AppEntry.TABLE_NAME+" app LEFT JOIN "+DBContract.NotificationEntry.TABLE_NAME+" noti " +
                "ON " +
                "app."+DBContract.AppEntry.COLUMN_PACKAGENAME+"=noti."+DBContract.AppEntry.COLUMN_PACKAGENAME+"" +
                " ORDER BY app."+DBContract.AppEntry.COLUMN_POSITION+" ASC";



        SQLiteDatabase baseDeDatos = dbHelper.getReadableDatabase();
        Cursor cursor = baseDeDatos.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                String label=cursor.getString(0);
                String name=cursor.getString(1);
                String packageName=cursor.getString(2);
                int position=cursor.getInt(3);
                int notification=cursor.getInt(4);


                if(notification == 0) {
                    appList.add(new LauncherApp(label, name, packageName));
                } else {
                    appList.add(new Notification(label, name, packageName, position));
                }

            } while (cursor.moveToNext());
        }
        return appList;

    }



    public int updateApp(LauncherApp app) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valuesForUpdate = new ContentValues();
        valuesForUpdate.put(DBContract.AppEntry.COLUMN_LABEL, app.getLabel());
        valuesForUpdate.put(DBContract.AppEntry.COLUMN_NAME, app.getName());
        valuesForUpdate.put(DBContract.AppEntry.COLUMN_POSITION, app.getPosition());
        String whereClause = DBContract.AppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(app.getPackageName())};
        return db.update(DBContract.AppEntry.TABLE_NAME, valuesForUpdate, whereClause, args);
    }
    public int deleteApp(String packageName) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DBContract.AppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(packageName)};
        return db.delete(DBContract.AppEntry.TABLE_NAME, whereClause, args);
    }

    public void insertNotification(String packagename, int notification) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.NotificationEntry.COLUMN_PACKAGENAME, packagename);
        values.put(DBContract.NotificationEntry.COLUMN_NOTIFICATION, notification);
        db.insertWithOnConflict(DBContract.NotificationEntry.TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
    }
    public int deleteNotification(String packageName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DBContract.NotificationEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(packageName)};
        return db.delete(DBContract.NotificationEntry.TABLE_NAME, whereClause, args);
    }


}

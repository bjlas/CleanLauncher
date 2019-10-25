package com.android.onehuman.cleanlauncher.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.Header;
import com.android.onehuman.cleanlauncher.model.MenuApp;
import com.android.onehuman.cleanlauncher.model.HomeApp;
import com.android.onehuman.cleanlauncher.model.Notification;

import java.util.ArrayList;


public class DBController {

    private DBHelper dbHelper;

    public DBController(Context context)
    {
        dbHelper = DBHelper.getInstance(context);
    }

    //COMMON
    public ArrayList<RowType> getHomeApps() {

        ArrayList<RowType> appList = new ArrayList<>();

        String launcherNotificationsSQL ="SELECT " +
                "launcher."+DBContract.HomeAppEntry.COLUMN_PACKAGENAME+"," +
                "noti."+DBContract.NotificationEntry.COLUMN_NOTIFICATION+" " +
                "FROM " +
                ""+DBContract.HomeAppEntry.TABLE_NAME+" launcher " +
                "LEFT JOIN " +
                ""+DBContract.NotificationEntry.TABLE_NAME+" noti " +
                "ON " +
                "launcher."+DBContract.HomeAppEntry.COLUMN_PACKAGENAME+" = noti."+DBContract.NotificationEntry.COLUMN_PACKAGENAME+" " +
                " ORDER BY launcher."+DBContract.HomeAppEntry.COLUMN_POSITION+" asc";

        String nonLauncherNotificationsSQL = "SELECT " +
                "noti."+DBContract.NotificationEntry.COLUMN_PACKAGENAME+", " +
                "noti."+DBContract.NotificationEntry.COLUMN_NOTIFICATION+" " +
                "FROM "+DBContract.NotificationEntry.TABLE_NAME+" noti " +
                "LEFT JOIN " +
                ""+DBContract.HomeAppEntry.TABLE_NAME+" launcher " +
                "ON " +
                "noti."+DBContract.NotificationEntry.COLUMN_PACKAGENAME+"=launcher."+DBContract.HomeAppEntry.COLUMN_PACKAGENAME+" " +
                "WHERE launcher."+DBContract.HomeAppEntry.COLUMN_PACKAGENAME+" IS NULL";



        String selectQuery = "SELECT apps."+DBContract.AppEntry.COLUMN_PACKAGENAME+", apps."+DBContract.AppEntry.COLUMN_NAME+", apps."+DBContract.AppEntry.COLUMN_LABEL+", launcherNotifications."+DBContract.NotificationEntry.COLUMN_NOTIFICATION+" " +
                "FROM " +
                "("+launcherNotificationsSQL+") launcherNotifications, " +
                ""+DBContract.AppEntry.TABLE_NAME+" apps " +
                "WHERE launcherNotifications.packageName=apps."+DBContract.AppEntry.COLUMN_PACKAGENAME+" " +
                "UNION ALL " +
                "SELECT apps."+DBContract.AppEntry.COLUMN_PACKAGENAME+", apps."+DBContract.AppEntry.COLUMN_NAME+", apps."+DBContract.AppEntry.COLUMN_LABEL+", nonLauncherNotifications."+DBContract.NotificationEntry.COLUMN_NOTIFICATION+" " +
                "FROM ("+nonLauncherNotificationsSQL+") nonLauncherNotifications, "+DBContract.AppEntry.TABLE_NAME+" apps " +
                "WHERE nonLauncherNotifications.packageName=apps."+DBContract.AppEntry.COLUMN_PACKAGENAME+"";



        SQLiteDatabase baseDeDatos = dbHelper.getReadableDatabase();
        Cursor cursor = baseDeDatos.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                String label=cursor.getString(0);
                String name=cursor.getString(1);
                String packageName=cursor.getString(2);
                int notification=cursor.getInt(3);


                if(notification == 0) {
                    appList.add(new HomeApp(label, name, packageName,0));
                } else {
                    appList.add(new Notification(label, name, packageName, notification));
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return appList;

    }
    public ArrayList<RowType> getMenuApps() {

        ArrayList<RowType> appList = new ArrayList<>();

        String selectQuery = "SELECT " +
                "app."+DBContract.AppEntry.COLUMN_PACKAGENAME+", " +
                "app."+DBContract.AppEntry.COLUMN_NAME+", " +
                "app."+DBContract.AppEntry.COLUMN_LABEL+", " +
                "noti."+DBContract.NotificationEntry.COLUMN_NOTIFICATION+" " +
                "FROM " +
                ""+DBContract.AppEntry.TABLE_NAME+" app " +
                "LEFT JOIN " +
                ""+DBContract.NotificationEntry.TABLE_NAME+" noti " +
                "ON noti."+DBContract.NotificationEntry.COLUMN_PACKAGENAME+"=app."+DBContract.AppEntry.COLUMN_PACKAGENAME+" " +
                "ORDER BY app."+DBContract.AppEntry.COLUMN_LABEL+" ASC";


        SQLiteDatabase baseDeDatos = dbHelper.getReadableDatabase();
        Cursor cursor = baseDeDatos.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                String packageName=cursor.getString(0);
                String name=cursor.getString(1);
                String label=cursor.getString(2);
                int notification=cursor.getInt(3);


                if(notification == 1) {
                    appList.add(new Notification(packageName, name, label, notification));
                } else {
                    appList.add(new MenuApp(packageName, name, label));
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return appList;

    }
    public ArrayList<Header> getAlphabetApps() {

        ArrayList<Header> headerList = new ArrayList<>();

        String selectQuery = "SELECT " +
                "DISTINCT " +
                "substr(upper(app."+DBContract.AppEntry.COLUMN_LABEL+"),1,1) " +
                "FROM " +
                ""+DBContract.AppEntry.TABLE_NAME+" app";

        SQLiteDatabase baseDeDatos = dbHelper.getReadableDatabase();
        Cursor cursor = baseDeDatos.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                headerList.add(new Header(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return headerList;

    }
    public long getAppTableSize(String tableName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, tableName);
        return count;
    }

    //APP
    public long insertApp(String packagename, String name, String label) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.AppEntry.COLUMN_PACKAGENAME, packagename);
        values.put(DBContract.AppEntry.COLUMN_NAME, name);
        values.put(DBContract.AppEntry.COLUMN_LABEL, label);

        return db.insert(DBContract.AppEntry.TABLE_NAME, null, values);

    }
    public int deleteApp(String packageName) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DBContract.AppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(packageName)};
        return db.delete(DBContract.AppEntry.TABLE_NAME, whereClause, args);
    }

    //HOMEAPPS
    public long insertHomeApp(String packagename) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.HomeAppEntry.COLUMN_PACKAGENAME, packagename);
        values.put(DBContract.HomeAppEntry.COLUMN_POSITION, getAppTableSize(DBContract.HomeAppEntry.TABLE_NAME));
        return db.insertWithOnConflict(DBContract.HomeAppEntry.TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_IGNORE);
    }
    public int deleteHomeApp(String packageName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DBContract.HomeAppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(packageName)};
        return db.delete(DBContract.HomeAppEntry.TABLE_NAME, whereClause, args);
    }
    public int updateHomeApp(String packageName, int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valuesForUpdate = new ContentValues();
        valuesForUpdate.put(DBContract.HomeAppEntry.COLUMN_POSITION, position);
        String whereClause = DBContract.HomeAppEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(packageName)};
        return db.update(DBContract.HomeAppEntry.TABLE_NAME, valuesForUpdate, whereClause, args);
    }

    //NOTIFICATIONS
    public long insertNotification(String packagename, int notification) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.NotificationEntry.COLUMN_PACKAGENAME, packagename);
        values.put(DBContract.NotificationEntry.COLUMN_NOTIFICATION, notification);
        return db.insertWithOnConflict(DBContract.NotificationEntry.TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_IGNORE);
    }
    public int deleteNotification(String packageName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DBContract.NotificationEntry.COLUMN_PACKAGENAME+" = ?";
        String[] args = {String.valueOf(packageName)};
        return db.delete(DBContract.NotificationEntry.TABLE_NAME, whereClause, args);
    }


}

package com.android.onehuman.cleanlauncher.task;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.android.onehuman.cleanlauncher.interfaces.OnTaskCompleted;
import com.android.onehuman.cleanlauncher.persistence.DBController;
import com.android.onehuman.cleanlauncher.utils.AppUtils;



public class PopulateDBAppsTask extends AsyncTask<String, Void, String> {

    private OnTaskCompleted onTaskCompleted;
    private AppUtils appUtils;

    public PopulateDBAppsTask(Context c, OnTaskCompleted otc){
        this.onTaskCompleted=otc;
        appUtils=new AppUtils(c);
    }


    @Override
    protected String doInBackground(String... params) {
        appUtils.savePacsToDB();
        return null;
    }


    @Override
    protected void onPostExecute(String result){
        onTaskCompleted.onTaskCompleted();
    }
}

package com.android.onehuman.cleanlauncher.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.MenuApp;
import com.android.onehuman.cleanlauncher.model.Header;
import com.android.onehuman.cleanlauncher.model.Notification;
import com.android.onehuman.cleanlauncher.persistence.DBController;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppUtils {
    private DBController dbController;
    private PackageManager packageManager;

    public AppUtils(Context c){
        dbController = new DBController(c);
        this.packageManager=c.getPackageManager();
    }

    public List<RowType> generateMenuList() {
        String previous="";
        String label="";

        List<RowType> dbApps = dbController.getMenuApps();
        List<RowType> menuList = new ArrayList<>();


        for(int index=0;index<dbApps.size();index++){

            if (dbApps.get(index) instanceof Notification) {
                label = ((Notification) dbApps.get(index)).getLabel();
            } else {
                label = ((MenuApp) dbApps.get(index)).getLabel();
            }

            String header=cleanAccents(label).subSequence(0, 1).toString();
            if(index == 0 || isHeader(header, previous)) {
                previous=header;
                menuList.add(new Header(header));
            }
            menuList.add(dbApps.get(index));
        }
        return menuList;
    }

    public List<Header> generateAlphabeticalList() {
        return dbController.getAlphabetApps();
    }


    public String cleanAccents(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }

    public boolean isHeader(String label, String previous) {
        return label.charAt(0) != previous.charAt(0);
    }

    public void savePacsToDB() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pacsList = packageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(pacsList, new ResolveInfo.DisplayNameComparator(packageManager));

        for(int index=0;index<pacsList.size();index++){

            dbController.insertApp(
                    pacsList.get(index).activityInfo.packageName,
                    pacsList.get(index).activityInfo.name,
                    pacsList.get(index).loadLabel(packageManager).toString()

            );
        }
    }

    public void truncateTabe() {

        dbController.insertApp();

    }

}
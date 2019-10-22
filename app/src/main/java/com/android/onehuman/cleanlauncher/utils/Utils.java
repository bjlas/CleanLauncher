package com.android.onehuman.cleanlauncher.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.LauncherApp;
import com.android.onehuman.cleanlauncher.model.Header;
import com.android.onehuman.cleanlauncher.model.Notification;
import com.android.onehuman.cleanlauncher.persistence.DBController;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private DBController dbController;

    public Utils(Context c){
        dbController = new DBController(c);
    }


    public List<RowType> generateMenuList(List<ResolveInfo> pacsList, PackageManager packageManager) {
        String previous="";
        List<RowType> appsList = new ArrayList<>();

        for(int index=0;index<pacsList.size();index++){
            String label = pacsList.get(index).loadLabel(packageManager).toString();
            String name=pacsList.get(index).activityInfo.name;
            String packageName=pacsList.get(index).activityInfo.packageName;


            String header=cleanAccents(label).subSequence(0, 1).toString();

            if(index == 0 || isHeader(header, previous)) {
                previous=header;
                appsList.add(new Header(previous));
            }

            if(dbController.checkNotifications(packageName)) {
                appsList.add(new Notification(label, name, packageName, 1));
            } else {
                appsList.add(new LauncherApp(label, name, packageName));
            }
        }
        return appsList;
    }

    public List<Header> generateAlphabeticalList(List<ResolveInfo> pacsList, PackageManager packageManager) {
        String previous="";
        List<Header> alphabeticalList = new ArrayList<>();

        for(int index=0;index<pacsList.size();index++){
            String label = pacsList.get(index).loadLabel(packageManager).toString();
            label=cleanAccents(label);

            if(index == 0 || isHeader(label, previous)) {
                previous=label.subSequence(0, 1).toString();
                alphabeticalList.add(new Header(previous));
            }

        }
        return alphabeticalList;
    }

    public String cleanAccents(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }

    public boolean isHeader(String label, String previous) {
        return label.charAt(0) != previous.charAt(0);
    }

}
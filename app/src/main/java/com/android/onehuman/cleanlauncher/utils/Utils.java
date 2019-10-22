package com.android.onehuman.cleanlauncher.utils;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.android.onehuman.cleanlauncher.interfaces.RowType;
import com.android.onehuman.cleanlauncher.model.App;
import com.android.onehuman.cleanlauncher.model.Header;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static List<RowType> generateMenuList(List<ResolveInfo> pacsList, PackageManager packageManager) {
        String previous="";
        List<RowType> appsList = new ArrayList<>();

        for(int index=0;index<pacsList.size();index++){
            String label = pacsList.get(index).loadLabel(packageManager).toString();
            label=cleanAccents(label);
            if(index == 0 || isHeader(label, previous)) {
                previous=label.subSequence(0, 1).toString();
                appsList.add(new Header(previous));
            }

            App app = new App(
                    label,
                    pacsList.get(index).activityInfo.name,
                    pacsList.get(index).activityInfo.packageName
            );
            appsList.add(app);
        }
        return appsList;
    }

    public static List<Header> generateAlphabeticalList(List<ResolveInfo> pacsList, PackageManager packageManager) {
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

    public static String cleanAccents(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }

    public static boolean isHeader(String label, String previous) {
        return label.charAt(0) != previous.charAt(0);
    }

}
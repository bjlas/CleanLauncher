package com.android.onehuman.cleanlauncher.interfaces;

import com.android.onehuman.cleanlauncher.model.App;

import java.util.ArrayList;
import java.util.List;

public interface Persistence {

    int insert(String label, String name, String packagename);
    int delete(String id);
    ArrayList<App> getAll();

}

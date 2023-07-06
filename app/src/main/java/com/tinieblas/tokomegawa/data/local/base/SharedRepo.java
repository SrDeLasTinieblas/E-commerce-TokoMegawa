package com.tinieblas.tokomegawa.data.local.base;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public abstract class SharedRepo {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public SharedRepo(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    protected void putString(String key, String value){
        editor.putString(key, value);
        editor.apply();
    }

    protected String getString(String key){
        return sharedPreferences.getString(key, "");
    }

    protected void putStringSet(String id, Set<String> values){
        editor.putStringSet(id, values);
        editor.apply();
    }

    protected Set<String> getStringSet(String key){
        return sharedPreferences.getStringSet(key, new HashSet<>());
    }

    protected void clear(){
        editor.clear();
        editor.apply();
    }
}

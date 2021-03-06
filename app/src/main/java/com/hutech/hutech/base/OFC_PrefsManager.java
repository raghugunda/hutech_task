package com.hutech.hutech.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hutech.hutech.Utils.Constants_urls;

import java.util.Map;
/**
 * Created by Raghavendra on 6/3/2021.
 */
public class OFC_PrefsManager implements Constants_urls {

    private Context context;
    private SharedPreferences prefsManager;

    public OFC_PrefsManager(Context applicationContext) {
        context = applicationContext;
        prefsManager = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void save(String key, String value) {
        SharedPreferences.Editor editor = prefsManager.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = prefsManager.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String get(String... key){
        if(key.length == 1) {
            return prefsManager.getString(key[0], null);
        }else{
            return prefsManager.getString(key[0], key[1]);
        }
    }

    public void clearAllPrefs() {
        prefsManager.edit().clear().apply();
    }

    public boolean hasKey(String key) {
        return prefsManager.contains(key);
    }

    public void removeKey(String key) {
        SharedPreferences.Editor editor = prefsManager.edit();
        editor.remove(key);
        editor.apply();
    }

    public Map<String, ?> getAllPrefs(){
        return prefsManager.getAll();
    }
}


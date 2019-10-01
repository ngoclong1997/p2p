package com.p2p.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.p2p.models.User;

public class PrefsUtils {

    public static final String CURRENT_USER = "CURRENT_USER";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor getPreferencesEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static String getPreferenceValue(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static User getPreferenceValue(Context context, String key, User defValue) {
        String json = getPreferences(context).getString(key, null);
        if (json == null) return defValue;
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, User.class);
        } catch (Exception e) {
            return defValue;
        }
    }


    public static int getPreferenceValue(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static boolean getPreferenceValue(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void setPreferenceValue(Context context, String key, User prefsValue) {
        Gson gson = new Gson();
        String value = gson.toJson(prefsValue);
        setPreferenceValue(context, key, value);
    }

    public static void setPreferenceValue(Context context, String key, String prefsValue) {
        getPreferencesEditor(context).putString(key, prefsValue).apply();
    }

    public static void setPreferenceValue(Context context, String key, int prefsValue) {
        getPreferencesEditor(context).putInt(key, prefsValue).apply();
    }

    public static void setPreferenceValue(Context context, String key, boolean prefsValue) {
        getPreferencesEditor(context).putBoolean(key, prefsValue).apply();
    }


    public static boolean containsPreferenceKey(Context context, String key) {
        return getPreferences(context).contains(key);
    }

    public static void removePreferenceValue(Context context, String key) {
        getPreferencesEditor(context).remove(key).apply();
    }
}

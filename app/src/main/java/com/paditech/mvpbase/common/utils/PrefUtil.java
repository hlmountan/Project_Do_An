package com.paditech.mvpbase.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * dienquang_android_user
 * <p>
 * Created by Paditech on 2/24/2017.
 * Copyright (c) 2017 Paditech. All rights reserved.
 */
public class PrefUtil {

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void savePreferences(Context context, String key, boolean content) {
        final SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(key, content);
        editor.apply();
    }

    public static void savePreferences(Context context, String key, String content) {
        final SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(key, content);
        editor.apply();
    }

    public static void savePreferences(Context context, String key, int content) {
        final SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(key, content);
        editor.apply();
    }

    public static boolean getPreferences(Context context, String key) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getBoolean(key, true);
    }

    public static String getPreferences(Context context, String key, String defVal) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(key, defVal);
    }

    public static int getPreferences(Context context, String key, int defVal) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getInt(key, defVal);
    }

}

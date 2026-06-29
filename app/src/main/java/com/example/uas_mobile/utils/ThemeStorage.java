package com.example.uas_mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeStorage {
    private static final String PREF_NAME = "theme_prefs";
    private static final String KEY_IS_DARK = "is_dark";
    private final SharedPreferences sharedPreferences;

    public ThemeStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setDarkMode(boolean isDark) {
        sharedPreferences.edit().putBoolean(KEY_IS_DARK, isDark).apply();
    }

    public boolean isDarkMode() {
        return sharedPreferences.getBoolean(KEY_IS_DARK, false);
    }
}

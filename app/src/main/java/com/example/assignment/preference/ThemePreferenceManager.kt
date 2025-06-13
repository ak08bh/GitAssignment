package com.example.assignment.preference

import android.content.Context
import android.content.SharedPreferences

class ThemePreferenceManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveThemePreference(isDark: Boolean) {
        prefs.edit().putBoolean("dark_mode", isDark).apply()
    }

    fun loadThemePreference(): Boolean {
        return prefs.getBoolean("dark_mode", false)
    }
}
package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import android.content.SharedPreferences



const val PLM_PREFERENCES = "playlist_maker_preferences"
const val THEME_KEY = "theme_key"

class App : Application() {

    var darkTheme = false
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate() {

        sharedPreferences = getSharedPreferences(PLM_PREFERENCES,MODE_PRIVATE)
        darkTheme = sharedPreferences.getBoolean(THEME_KEY,darkTheme)
        super.onCreate()
        switchTheme(darkTheme)
        if (darkTheme == true) {
        } else {  }

    }



    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        sharedPreferences.edit().putBoolean(THEME_KEY, darkTheme).apply()
    }

}
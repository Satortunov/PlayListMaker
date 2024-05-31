package com.example.playlistmaker

import android.app.Application
import android.content.res.Configuration
import android.os.Bundle

class PlaylistMaker : Application() {
    // Вызывается при старте приложения, до того как инициализируются другие объекты
    override fun onCreate() {
        super.onCreate()
    }

    // Вызывается, когда конфигурация телефона изменена
    override fun onConfigurationChanged ( newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    // Вызывается, когда заканчивается память в системе
    override fun onLowMemory() {
        super.onLowMemory()
    }


}
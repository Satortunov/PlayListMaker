package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

       val imageViewSettings = findViewById<ImageView>(R.id.asSettings)
        imageViewSettings.setOnClickListener {
            finish()
        }

        val imageViewShare = findViewById<ImageView>(R.id.asShare)
            imageViewShare.setOnClickListener {
                val intentShare = Intent(Intent.ACTION_SEND);
                val stringShare = "https://practicum.yandex.ru/android-developer/"
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT, stringShare)
                startActivity(Intent.createChooser(intentShare, "Sent Message"))
            }

        val imageViewSupport = findViewById<ImageView>(R.id.asSupport)
        imageViewSupport.setOnClickListener {
            val intentSupport = Intent(Intent.ACTION_SEND);
            intentSupport.setType("text/html");
            intentSupport.putExtra(Intent.EXTRA_EMAIL, "satortunov@yandex.ru");
            intentSupport.putExtra(Intent.EXTRA_SUBJECT, "Сообщение разработчикам и разработчицам приложения Playlist Maker");
            intentSupport.putExtra(Intent.EXTRA_TEXT, "Спасибо разработчикам и разработчицам за крутое приложение!");
            startActivity(intentSupport)
        }
        val imageViewUserAgreement = findViewById<ImageView>(R.id.asUserAgreement)
        imageViewUserAgreement.setOnClickListener {
            val urlString = "https://yandex.ru/legal/practicum_offer/"
            val intentUserAgreement: Intent = Intent(Intent.ACTION_VIEW)
            intentUserAgreement.setData(Uri.parse(urlString))
            startActivity(intentUserAgreement)
        }
    }
}
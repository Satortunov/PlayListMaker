package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val intentSupport = Intent().apply {
                action = Intent.ACTION_SENDTO
                setType("text/html");
                data = Uri.parse("mailto:satortunov@yandex.ru")
                putExtra(Intent.EXTRA_SUBJECT,"Сообщение разработчикам и разработчицам приложения Playlist Maker");
                putExtra(Intent.EXTRA_TEXT,"Спасибо разработчикам и разработчицам за крутое приложение!"
                );
            }
            startActivity(intentSupport)
        }
        val imageViewUserAgreement = findViewById<ImageView>(R.id.asUserAgreement)
        imageViewUserAgreement.setOnClickListener {
               val displayIntent = Intent(this, AgreementActivity::class.java)
            startActivity(displayIntent)
        }
    }
}
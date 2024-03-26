package com.example.playlistmaker

import android.content.Intent
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

        val imageViewShare = findViewById<ImageView>(R.id.asShare)
            imageViewShare.setOnClickListener {
                val intentShare = Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                val stringShare = "https://practicum.yandex.ru/android-developer/"
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                intent.putExtra(Intent.EXTRA_TEXT, stringShare);
                startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
            }


        val imageViewSupport = findViewById<ImageView>(R.id.asSupport)
        imageViewSupport.setOnClickListener {
            val intentSupport = Intent(Intent.ACTION_SEND);
            intentSupport.setType("text/html");
            intentSupport.putExtra(Intent.EXTRA_EMAIL, "satortunov@yandex.ru");
            intentSupport.putExtra(Intent.EXTRA_SUBJECT, "Сообщение разработчикам и разработчицам приложения Playlist Maker");
            intentSupport.putExtra(Intent.EXTRA_TEXT, "Спасибо разработчикам и разработчицам за крутое приложение!");
            startActivity(Intent.createChooser(intentSupport, "Send Email"))
        }

    }
}
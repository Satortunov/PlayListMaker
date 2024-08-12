package com.example.playlistmaker

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class AudioPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)
        val imageViewAudioPlayer = findViewById<ImageView>(R.id.audioPlayerBack)
        imageViewAudioPlayer.setOnClickListener {
            finish()
        }


    }//onCreate
}
package com.example.playlistmaker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class AudioPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)

        val searchedHistory = SearchHistory()
        var track = searchedHistory.readTrackList(getSharedPreferences(PLM_PREFERENCES, MODE_PRIVATE)).get(0)

        val imageViewAudioPlayer = findViewById<ImageView>(R.id.audioPlayerBack)
        imageViewAudioPlayer.setOnClickListener {
            finish()
        }

       val trackName = findViewById<TextView>(R.id.trackName)
        trackName.text = track.trackName

        val artistName = findViewById<TextView>(R.id.artistName)
        artistName.text = track.artistName

    }//onCreate
}
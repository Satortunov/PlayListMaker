package com.example.playlistmaker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.utils.loadImageFromInternet


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

        //вывод данных трека

        val trackName = findViewById<TextView>(R.id.trackName)
        if (track.trackName != null) trackName.text = track.trackName
        else trackName.text = "Без названия"

        val artistName = findViewById<TextView>(R.id.artistName)
        if (track.artistName != null) artistName.text = track.artistName
        else artistName.text = "Неизвестный исполнитель"


        val trackTimeMillis = findViewById<TextView>(R.id.trackTimeMillis)
        if (track.trackTimeMillis != null) trackTimeMillis.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        else trackTimeMillis.text = "Без времени"

        val artworkUrl100 = findViewById<ImageView>(R.id.artworkUrl100)
        loadImageFromInternet(artworkUrl100, track.artworkUrl100, R.drawable.placeholder.toDrawable(), R.dimen.size_dp_2, artworkUrl100)

        val collectionName = findViewById<TextView>(R.id.collectionName)
        if (track.collectionName != null) collectionName.text = track.collectionName
        else collectionName.text = "Нет названия"

        val releaseDate = findViewById<TextView>(R.id.releaseDate)
        if (track.releaseDate != null) releaseDate.text = track.releaseDate.substring(0, 4)
        else releaseDate.text = "Неизвестный год"

        val primaryGenreName = findViewById<TextView>(R.id.primaryGenreName)
        if (track.primaryGenreName != null) primaryGenreName.text = track.primaryGenreName
        else primaryGenreName.text = "Неизвестный жанр"

        val country = findViewById<TextView>(R.id.country)
        if (track.country != null) country.text = track.country
        else country.text = "Неизвестная страна"

        //вывод данных трека

        searchedHistory.setTrackList(getSharedPreferences(PLM_PREFERENCES, MODE_PRIVATE), track)

    }//onCreate

}
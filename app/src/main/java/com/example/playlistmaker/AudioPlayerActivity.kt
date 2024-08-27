package com.example.playlistmaker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.utils.*



class AudioPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)

        val audioPlayerBack = findViewById<ImageView>(R.id.audioPlayerBack)
        audioPlayerBack.setOnClickListener {
            finish()
        }

        val searchedHistory = SearchHistory()
        var track = intent.getSerializableExtra("track") as? Track


        //вывод данных трека

        val trackName = findViewById<TextView>(R.id.trackName)
        if (track?.trackName != null) trackName.text = track.trackName
        else trackName.text = R.string.no_name.toString()

        val artistName = findViewById<TextView>(R.id.artistName)
        if (track?.artistName != null) artistName.text = track.artistName
        else artistName.text = R.string.unknown_musician.toString()


        val trackTimeMillis = findViewById<TextView>(R.id.trackTimeMillis)
        if (track?.trackTimeMillis != null) trackTimeMillis.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        else trackTimeMillis.text = R.string.no_data.toString()

        val artworkUrl100 = findViewById<ImageView>(R.id.artworkUrl100)
        Glide.with(artworkUrl100)
            .load(track?.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(artworkUrl100.context.dpToPx(artworkUrl100.resources.getDimensionPixelSize(R.dimen.size_dp_2)).toInt()))
            .into(artworkUrl100)

        val collectionName = findViewById<TextView>(R.id.collectionName)
        if (track?.collectionName != null) collectionName.text = track.collectionName
        else collectionName.text = R.string.no_data.toString()

        val releaseDate = findViewById<TextView>(R.id.releaseDate)
        if (track?.releaseDate != null) releaseDate.text = track.releaseDate.substring(0, 4)
        else releaseDate.text = R.string.no_data.toString()

        val primaryGenreName = findViewById<TextView>(R.id.primaryGenreName)
        if (track?.primaryGenreName != null) primaryGenreName.text = track.primaryGenreName
        else primaryGenreName.text = R.string.no_data.toString()

        val country = findViewById<TextView>(R.id.country)
        if (track?.country != null) country.text = track.country
        else country.text = R.string.no_data.toString()

        //вывод данных трека

        //searchedHistory.setTrackList(getSharedPreferences(PLM_PREFERENCES, MODE_PRIVATE), track)

    }//onCreate

}
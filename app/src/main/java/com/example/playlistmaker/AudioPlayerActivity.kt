package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.utils.*

@Suppress("DEPRECATION")
class AudioPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)

        val audioPlayerBack = findViewById<ImageView>(R.id.audioPlayerBack)
        audioPlayerBack.setOnClickListener {
            finish()
        }

        val intent: Intent = getIntent()
        var track = intent.getSerializableExtra(SAVED_TRACK) as? Track

        //вывод данных трека
        val trackName = findViewById<TextView>(R.id.trackName)
        trackName.text = track?.trackName?: R.string.no_name.toString()

        val artistName = findViewById<TextView>(R.id.artistName)
        artistName.text = track?.artistName?: R.string.unknown_musician.toString()

        val trackTimeMillis = findViewById<TextView>(R.id.trackTimeMillis)
        trackTimeMillis.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track?.trackTimeMillis)?: R.string.no_data.toString()

        val artworkUrl100 = findViewById<ImageView>(R.id.artworkUrl100)
        Glide.with(artworkUrl100)
            .load(track?.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(
                RoundedCorners(
                    artworkUrl100.context.dpToPx(
                        artworkUrl100.resources.getDimensionPixelSize(
                            R.dimen.size_dp_2
                        )
                    ).toInt()
                )
            )
            .into(artworkUrl100)

        val collectionName = findViewById<TextView>(R.id.collectionName)
        collectionName.text = track?.collectionName?: R.string.no_data.toString()

        val releaseDate = findViewById<TextView>(R.id.releaseDate)
        releaseDate.text = track?.releaseDate?.substring(0, 4)?: R.string.no_data.toString()

        val primaryGenreName = findViewById<TextView>(R.id.primaryGenreName)
        primaryGenreName.text = track?.primaryGenreName?: R.string.no_data.toString()

        val country = findViewById<TextView>(R.id.country)
        country.text = track?.country?: R.string.no_data.toString()
        //вывод данных трека


    }//onCreate

}
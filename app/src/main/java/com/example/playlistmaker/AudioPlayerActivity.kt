package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
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

    private var playerState = STATE_DEFAULT
    private val mediaPlayer = MediaPlayer()
    private var mainThreadHandler: Handler? = null
    private lateinit var previewUrl: String
    private lateinit var playButton: ImageButton
    private lateinit var soundTime: TextView

    private fun preparePlayer(sourceUrl: String) {
        mediaPlayer.start()
        mediaPlayer.setDataSource(sourceUrl)
        mediaPlayer.prepareAsync()
         mediaPlayer.setOnPreparedListener {
             playButton.isEnabled = true
             playerState = STATE_PREPARED
         }
         mediaPlayer.setOnCompletionListener {
             playerState = STATE_PREPARED
         }
    }

    fun progressTime(): Runnable {
        return object : Runnable {
            override fun run() {
                when (playerState) {
                    STATE_PLAYING -> {
                        soundTime.text = SimpleDateFormat( TIME_FORMAT, Locale.getDefault()).format(mediaPlayer.currentPosition)
                        mainThreadHandler?.postDelayed(this, DELAY_MILLIS)
                    }
                    STATE_PAUSED -> { mainThreadHandler?.removeCallbacks(this) }
                    STATE_PREPARED -> {mainThreadHandler?.removeCallbacks(this)
                        playButton.setImageResource(R.drawable.play)
                        soundTime.text = START_TIME }
                }
            }
        }
    }

    fun startPlayer() {
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.stop)
        playerState = STATE_PLAYING
        mainThreadHandler?.post(progressTime())
    }

    fun pausePlayer() {
        mediaPlayer.pause()
        playButton.setImageResource(R.drawable.play)
        playerState = STATE_PAUSED
    }

    fun playbackControl() {
       when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }
    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)

        val audioPlayerBack = findViewById<ImageView>(R.id.audioPlayerBack)
        audioPlayerBack.setOnClickListener { finish() }

        val intent: Intent = getIntent()
        var track = intent.getSerializableExtra(SAVED_TRACK) as? Track

        playButton = findViewById(R.id.playButton)
        soundTime = findViewById(R.id.soundTime)

        //вывод данных трека
        val trackName = findViewById<TextView>(R.id.trackName)
        trackName.text = track?.trackName?: R.string.no_name.toString()

        val artistName = findViewById<TextView>(R.id.artistName)
        artistName.text = track?.artistName?: R.string.unknown_musician.toString()

        val trackTimeMillis = findViewById<TextView>(R.id.trackTimeMillis)
        trackTimeMillis.text = SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(track?.trackTimeMillis)?: R.string.no_data.toString()

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
        previewUrl = track?.previewUrl.toString()
         //вывод данных трека


        mainThreadHandler = android.os.Handler(Looper.getMainLooper())

        if (track != null) { preparePlayer(previewUrl) }
        playButton.setOnClickListener { playbackControl() }

    }//onCreate

}
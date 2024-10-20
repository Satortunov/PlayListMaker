package com.example.playlistmaker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.playlistmaker.R

class MediatecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediatec)
        val imageViewMediatec = findViewById<ImageView>(R.id.ameMediatec)
        imageViewMediatec.setOnClickListener {
            finish()
        }
    }
}
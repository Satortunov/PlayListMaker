package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_search = findViewById<Button>(R.id.amSearch)
        button_search.setOnClickListener {
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }


        val button_mediatec = findViewById<Button>(R.id.amMediatec)
        button_mediatec.setOnClickListener {
            val displayIntent = Intent(this, MediatecActivity::class.java)
            startActivity(displayIntent)
        }

        val button_settings = findViewById<Button>(R.id.amSettings)
        button_settings.setOnClickListener {
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)
        }

    }

}
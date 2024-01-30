package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_search = findViewById<Button>(R.id.am_search)
        button_search.setOnClickListener {
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }


        val button_mediatec = findViewById<Button>(R.id.am_mediatec)
        button_mediatec.setOnClickListener {
            val displayIntent = Intent(this, MediatecActivity::class.java)
            startActivity(displayIntent)


        }

        val button_settings = findViewById<Button>(R.id.am_settings)
        button_settings.setOnClickListener {
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)

           // Toast.makeText(this@MainActivity, "Нажали на кнопку Настройки", Toast.LENGTH_SHORT).show()
        }

    }

}
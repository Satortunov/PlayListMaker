package com.example.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MediatecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediatec)

        val button_mediatec = findViewById<Button>(R.id.ase_mediatec)
        button_mediatec.setOnClickListener {
            finish()
        }
    }
}
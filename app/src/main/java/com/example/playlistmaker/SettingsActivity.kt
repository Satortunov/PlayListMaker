package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

       val imageViewSettings = findViewById<ImageView>(R.id.asSettings)
        imageViewSettings.setOnClickListener {
            finish()
        }

        val imageViewShare = findViewById<ImageView>(R.id.asShare)
            imageViewShare.setOnClickListener {
                val intentShare = Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app))
                startActivity(Intent.createChooser(intentShare, "Sent Message"))
            }

        val imageViewSupport = findViewById<ImageView>(R.id.asSupport)
        imageViewSupport.setOnClickListener {
            val intentSupport = Intent().apply {
                action = Intent.ACTION_SENDTO
                setType("text/html");
                data = Uri.parse(getString(R.string.support_address))
                putExtra(Intent.EXTRA_SUBJECT,"Тыр-тыр")
                putExtra(Intent.EXTRA_TEXT,getString(R.string.support_message))
            }
            startActivity(intentSupport)
        }
        val imageViewUserAgreement = findViewById<ImageView>(R.id.asUserAgreement)
        imageViewUserAgreement.setOnClickListener {
               val displayIntent = Intent(this, AgreementActivity::class.java)
            startActivity(displayIntent)
        }
    }
}
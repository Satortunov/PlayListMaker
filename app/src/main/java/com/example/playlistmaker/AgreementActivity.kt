package com.example.playlistmaker

import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AgreementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreement)
        val webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl("https://yandex.ru/legal/practicum_offer/")


        val imageViewSettings = findViewById<ImageView>(R.id.aseAgreement)
        imageViewSettings.setOnClickListener {
            finish()
        }

    }
}

package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial

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
            val intentSuppurt = Intent(Intent.ACTION_SENDTO)
            intentSuppurt.data = Uri.parse(getString(R.string.support_action))
            intentSuppurt.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_address)))
            intentSuppurt.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.support_theme))
            intentSuppurt.putExtra(Intent.EXTRA_TEXT, getString(R.string.support_message))
            startActivity(intentSuppurt)
        }

        val imageViewUserAgreement = findViewById<ImageView>(R.id.asUserAgreement)
        imageViewUserAgreement.setOnClickListener {
            val intentAgreement = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(getString(R.string.agreement_address))
            }
            startActivity(intentAgreement)

        }

        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)
        themeSwitcher.isChecked = getSharedPreferences(PLM_PREFERENCES, MODE_PRIVATE).getBoolean(THEME_KEY, false)

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
            (applicationContext as App).setThemeSwitch(checked)
        }

    }
}
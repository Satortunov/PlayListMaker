package com.example.playlistmaker

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val linearLayout = findViewById<LinearLayout>(R.id.container)
        val inputEditText = findViewById<EditText>(R.id.enterEditText)
        val searchImage = findViewById<ImageView>(R.id.search_Icon)

        inputEditText.setOnClickListener {
            inputEditText.setHint("")

        }
        searchImage.setOnClickListener {
            inputEditText.setText("")
            searchImage.visibility = searchImageVisibility("")
        }
        val imageViewSearch = findViewById<ImageView>(R.id.aseSearch)
        imageViewSearch.setOnClickListener {
            finish()
        }
    }

    private fun searchImageVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}
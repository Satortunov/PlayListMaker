package com.example.playlistmaker


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.*
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView


class SearchActivity : AppCompatActivity() {

    private var savedStr: String = SAVED_STR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

       val imageViewSearch = findViewById<ImageView>(R.id.aseSearch)
        imageViewSearch.setOnClickListener {
            finish()
        }

        val inputEditText = findViewById<EditText>(R.id.enterEditText)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        inputEditText.requestFocus()

        clearButton.setOnClickListener {
            inputEditText.setText("")
            val view: View? = this.currentFocus
            if (view != null) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
            }
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }
            override fun afterTextChanged(s: Editable?) {
                savedStr = inputEditText.text.toString()
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

        val searchedTrackList = findViewById<RecyclerView>(R.id.searchedTracks)
        searchedTrackList.adapter = trackList
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_STRING, savedStr)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedStr = savedInstanceState.getString(SAVED_STRING, savedStr)
    }

    companion object {
        const val SAVED_STRING = "SAVED_STRING"
        const val SAVED_STR = ""
    }
}
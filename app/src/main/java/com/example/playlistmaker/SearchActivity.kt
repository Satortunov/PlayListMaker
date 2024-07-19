package com.example.playlistmaker


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.*
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private var savedStr: String = SAVED_STR

    private val itunesAPIUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder().baseUrl(itunesAPIUrl).addConverterFactory(GsonConverterFactory.create()).build()

    private val iTunesAPI = retrofit.create(iTunesAPI::class.java)
    private lateinit var holderMessage: TextView
    private lateinit var holderImage: ImageView


    private val tracks = ArrayList<Track>()
    private val adapter = SearchAdapter(tracks)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

       val imageViewSearch = findViewById<ImageView>(R.id.aseSearch)
        imageViewSearch.setOnClickListener {
            finish()
        }
        holderMessage = findViewById(R.id.holderMessageText)
        holderImage = findViewById(R.id.holderMessageImage)

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


        fun findTracks() {

            if (inputEditText.text.isNotEmpty()) {
                tracks.clear()
                val imageHolder = findViewById<ImageView>(R.id.holderMessageImage)
                iTunesAPI.search(inputEditText.text.toString()).enqueue(object : Callback<TrackResponse> {
                    @SuppressLint("ResourceType")
                    override fun onResponse(call: Call<TrackResponse>,
                                            response: Response<TrackResponse>) =
                        if (response.code() == 200) {
                            if (response.body()?.results?.isNotEmpty() == true) {
                                tracks.clear()
                                tracks.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                            }
                            if (tracks.isEmpty()) {
                                imageHolder.setImageResource(R.drawable.nothingfind)
                                showMessage(getString(R.string.nothing_find),imageHolder)
                            } else {
                                imageHolder.setImageResource(R.drawable.nothingfind)
                                showMessage("", imageHolder)
                            }
                        } else {
                            imageHolder.setImageResource(R.drawable.connproplems)
                            showMessage(getString(R.string.connection_problems), imageHolder)
                        }

                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                        imageHolder.setImageResource(R.drawable.connproplems)
                        showMessage(getString(R.string.connection_problems), imageHolder)
                    }

                })
            }

        } //findTracks

        val searchedTracksList = findViewById<RecyclerView>(R.id.searchedTracks)
        searchedTracksList.adapter = adapter
        val reloadButton = findViewById<Button>(R.id.reloadButton)
        reloadButton.setOnClickListener {
            findTracks()
        }

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (inputEditText.text.isNotEmpty()) {
                   findTracks()
                }
                true
            }
            false
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)

    } //onCreate

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

    private fun showMessage(text: String, image: ImageView) {
        var buttonReload = findViewById<Button>(R.id.reloadButton)
        buttonReload.visibility = View.VISIBLE
        if (text.isNotEmpty()) {
            tracks.clear()
            holderMessage.text = text
            holderMessage.visibility = View.VISIBLE
            holderImage = image
            holderImage.visibility = View.VISIBLE
        } else {
            holderMessage.visibility = View.GONE
            holderImage.visibility = View.GONE
        }
    }


    companion object {
        const val SAVED_STRING = "SAVED_STRING"
        const val SAVED_STR = ""
    }
}
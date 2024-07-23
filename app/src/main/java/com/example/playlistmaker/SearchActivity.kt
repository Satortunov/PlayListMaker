package com.example.playlistmaker


import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.size
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
    private lateinit var reloadButton: Button
    private val tracks = ArrayList<Track>()
    private val historyOfSearch = SearchHistory()
    private lateinit var sharedPreferences: SharedPreferences
    private val adapter = SearchAdapter(tracks)
        { historyOfSearch.setTrackList(sharedPreferences, it)  }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val imageViewSearch = findViewById<ImageView>(R.id.aseSearch)
        val inputEditText = findViewById<EditText>(R.id.enterEditText)
        val clearIcon = findViewById<ImageView>(R.id.clearIcon)
        val searchedTracks = findViewById<RecyclerView>(R.id.searchedTracks)
        val searchedHistoryTracks = findViewById<RecyclerView>(R.id.searchedHistoryTracks)
        val clearHistoryButton = findViewById<Button>(R.id.clearHistoryButton)
        val searchedHistory = findViewById<LinearLayout>(R.id.searchHistory)
        val placeHolderMessage = findViewById<LinearLayout>(R.id.placeholderMessage)


        holderMessage = findViewById(R.id.holderMessageText)
        holderImage = findViewById(R.id.holderMessageImage)
        sharedPreferences = getSharedPreferences(PLM_PREFERENCES, MODE_PRIVATE)
        reloadButton = findViewById<Button>(R.id.reloadButton)
        inputEditText.requestFocus()
        searchedHistoryTracks.adapter = adapter

        //Toast.makeText(this@SearchActivity, historyOfSearch.readTrackList(sharedPreferences).size.toString(), Toast.LENGTH_SHORT).show()

        if (historyOfSearch.readTrackList(sharedPreferences).size > 0) {
            searchedHistoryTracks.adapter = SearchAdapter(historyOfSearch.readTrackList(sharedPreferences)) {}
            searchedHistory.isVisible = true
        }

        imageViewSearch.setOnClickListener {
            finish()
        }

        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            searchedHistory.isVisible = if (hasFocus && inputEditText.text.isEmpty() ) true else false
        }

        clearIcon.setOnClickListener {
            inputEditText.setText("")
            val view: View? = this.currentFocus
            if (view != null) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
            }
        }

        clearHistoryButton.setOnClickListener {
            historyOfSearch.clearAllTracks(sharedPreferences)
            searchedHistory.isVisible = false
            searchedHistoryTracks.adapter = SearchAdapter(historyOfSearch.readTrackList(sharedPreferences) ) {}

         }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearIcon.isVisible = clearButtonVisibility(s)
                val imageHolder = findViewById<ImageView>(R.id.holderMessageImage)
                if (inputEditText.hasFocus() && s?.isEmpty() == true) {
                    showMessage("", imageHolder)}
                searchedHistoryTracks.adapter = SearchAdapter(historyOfSearch.readTrackList(sharedPreferences)) {}
                searchedHistory.isVisible = if (historyOfSearch.readTrackList(sharedPreferences).isEmpty()) false else true

            }
            override fun afterTextChanged(s: Editable?) {
                savedStr = inputEditText.text.toString()
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

        fun findTracks() {
            placeHolderMessage.isVisible = false
            searchedHistory.isVisible = false
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


        reloadButton.setOnClickListener {
            findTracks()
            placeHolderMessage.isVisible = true

        }

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (inputEditText.text.isNotEmpty()) {
                    findTracks()
                    placeHolderMessage.isVisible = true
                    true
                }
            }
            false
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)

    } //onCreate

    private fun clearButtonVisibility(s: CharSequence?): Boolean {
        return if (s.isNullOrEmpty()) false else true
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
        if (text.isNotEmpty()) {
            tracks.clear()
            holderMessage.text = text
            holderMessage.isVisible = true
            holderImage = image
            holderImage.isVisible = true
            reloadButton.isVisible = true
        } else {
            holderMessage.isVisible = false
            holderImage.isVisible = false
            reloadButton.isVisible = false
        }
    }


    private companion object {
        const val SAVED_STRING = "SAVED_STRING"
        const val SAVED_STR = ""
    }
}
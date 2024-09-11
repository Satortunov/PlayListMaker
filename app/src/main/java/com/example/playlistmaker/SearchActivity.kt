package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("UNUSED_EXPRESSION", "DEPRECATION")
class SearchActivity : AppCompatActivity() {
    private var savedStr: String = SAVED_STR
    private val itunesAPIUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder().baseUrl(itunesAPIUrl).addConverterFactory(GsonConverterFactory.create()).build()
    private val iTunesAPI = retrofit.create(iTunesAPI::class.java)
    private lateinit var holderMessageText: TextView
    private lateinit var holderMessageImage: ImageView
    private lateinit var reloadButton: Button
    private var tracks = ArrayList<Track?>()
    private var historySearchTracks = ArrayList<Track?>()
    private val historyOfSearch = SearchHistory()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val adapter = SearchAdapter(tracks) {
            val displayIntent = Intent(this, AudioPlayerActivity::class.java)
            pressTrack(tracks, historyOfSearch, tracks.indexOf(it), sharedPreferences, displayIntent)
        }

        val searchBack = findViewById<ImageView>(R.id.searchBack)
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearIcon = findViewById<ImageView>(R.id.clearIcon)
        val searchedHistoryTracks = findViewById<RecyclerView>(R.id.searchedHistoryTracks)
        val clearHistoryButton = findViewById<Button>(R.id.clearHistoryButton)
        val searchHistory = findViewById<LinearLayout>(R.id.searchHistory)
        val placeHolderMessage = findViewById<LinearLayout>(R.id.placeholderMessage)


        holderMessageText = findViewById(R.id.holderMessageText)
        holderMessageImage = findViewById(R.id.holderMessageImage)
        sharedPreferences = getSharedPreferences(PLM_PREFERENCES, MODE_PRIVATE)
        reloadButton = findViewById<Button>(R.id.reloadButton)
        inputEditText.requestFocus()
        inputEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        searchedHistoryTracks.adapter = adapter
        inputEditText.isCursorVisible = false
        progressBar = findViewById(R.id.progressBar)
        historySearchTracks = historyOfSearch.readTrackList(sharedPreferences)


        if (historySearchTracks.size > 0) {
            searchedHistoryTracks.adapter = SearchAdapter(historySearchTracks)
            {
                val displayIntent = Intent(this, AudioPlayerActivity::class.java)
                pressTrack(historySearchTracks, historyOfSearch, historySearchTracks.indexOf(it), sharedPreferences, displayIntent)
            }
            placeHolderMessage.isVisible = false
            searchHistory.isVisible = true
        }

        searchBack.setOnClickListener {
            finish()
        }

        inputEditText.setOnClickListener {
            inputEditText.isCursorVisible = true
        }

        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            searchHistory.isVisible = hasFocus && inputEditText.text.isEmpty() && !historyOfSearch.readTrackList(sharedPreferences).isEmpty()
        }

        clearIcon.setOnClickListener {
            inputEditText.setText("")
            val view: View? = this.currentFocus
            if (view != null) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
            }
            placeHolderMessage.isVisible = false
            searchHistory.isVisible = inputEditText.text.isEmpty() && !historyOfSearch.readTrackList(sharedPreferences).isEmpty()
            historySearchTracks = historyOfSearch.readTrackList(sharedPreferences)
            searchedHistoryTracks.adapter = SearchAdapter(historySearchTracks) {
                val displayIntent = Intent(this, AudioPlayerActivity::class.java)
                pressTrack(historySearchTracks, historyOfSearch, historySearchTracks.indexOf(it), sharedPreferences, displayIntent)
            }
        }

        clearHistoryButton.setOnClickListener {
            historyOfSearch.clearAllTracks(sharedPreferences)
            adapter.notifyDataSetChanged()
            searchHistory.isVisible = false
        }
        fun findTracks() {
            placeHolderMessage.isVisible = false
            searchHistory.isVisible = false
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
                                placeHolderMessage.isVisible = true
                            }
                            if (tracks.isEmpty()) {
                                imageHolder.setImageResource(R.drawable.nothingfind)
                                showMessage(getString(R.string.nothing_find),imageHolder, false)
                            } else {
                                imageHolder.setImageResource(R.drawable.nothingfind)
                                showMessage("", imageHolder, false)
                            }
                        } else {
                            imageHolder.setImageResource(R.drawable.connproplems)
                            showMessage(getString(R.string.connection_problems), imageHolder, true)
                        }

                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                        imageHolder.setImageResource(R.drawable.connproplems)
                        showMessage(getString(R.string.connection_problems), imageHolder, true)
                    }
                })
            } else {
                placeHolderMessage.isVisible = false
                searchHistory.isVisible = true
            }
         } //findTracks

        val searchRunnable = Runnable { findTracks() }

        fun searchDebounce() {
            progressBar.isVisible = true
            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
            progressBar.isVisible = false
        }

        val simpleTextWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearIcon.isVisible = clearButtonVisibility(s)
                searchHistory.isVisible = false
                val imageHolder = findViewById<ImageView>(R.id.holderMessageImage)

                if (inputEditText.hasFocus() && s?.isEmpty() == true) {
                    handler.removeCallbacks(searchRunnable)
                    showMessage("", imageHolder,false)
                } else {
                    searchDebounce()
                    placeHolderMessage.isVisible = true
                }


                searchHistory.isVisible = !historyOfSearch.readTrackList(sharedPreferences).isEmpty()
                historySearchTracks = historyOfSearch.readTrackList(sharedPreferences)
                searchedHistoryTracks.adapter = SearchAdapter(historySearchTracks) {
                    val displayIntent = Intent(this@SearchActivity, AudioPlayerActivity::class.java)
                    pressTrack(historySearchTracks, historyOfSearch, historySearchTracks.indexOf(it), sharedPreferences, displayIntent)
                }

            }

            override fun afterTextChanged(s: Editable?) {
                savedStr = inputEditText.text.toString()
                if (inputEditText.text.isNotEmpty()) {
                    searchHistory.isVisible = false
                    searchDebounce()
                } else {
                    placeHolderMessage.isVisible = false
                    searchHistory.isVisible = !historyOfSearch.readTrackList(sharedPreferences).isEmpty()
                }
            }
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)


        val searchedTracksList = findViewById<RecyclerView>(R.id.searchedTracks)
        searchedTracksList.adapter = adapter
        adapter.notifyDataSetChanged()

        reloadButton.setOnClickListener {
            findTracks()
            placeHolderMessage.isVisible = true
        }

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (inputEditText.text.isNotEmpty()) {
                    progressBar.isVisible = true
                    searchDebounce()
                     //findTracks()
                    progressBar.isVisible = false
                    placeHolderMessage.isVisible = true

                }
                true
            }
            false
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)

    } //onCreate

    //работа с нажатием на трек
    private fun pressTrack(tracks: ArrayList<Track?>, searchHistory: SearchHistory, trackPosition: Int, sharedPreference: SharedPreferences, intent: Intent)
    {
        if (clickDebounce()) {
            searchHistory.setTrackList(sharedPreferences, tracks[trackPosition])
            intent.putExtra(SAVED_TRACK, tracks[trackPosition])
            startActivity(intent)
        }
    }

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

    private fun showMessage(text: String, image: ImageView, showButton: Boolean) {
        if (text.isNotEmpty()) {
            tracks.clear()
            holderMessageText.text = text
            holderMessageText.isVisible = true
            holderMessageImage = image
            holderMessageImage.isVisible = true
            reloadButton.isVisible = showButton
        } else {
            holderMessageText.isVisible = false
            holderMessageImage.isVisible = false
            reloadButton.isVisible = false
        }
    }

    private companion object {
        const val SAVED_STRING = "SAVED_STRING"
        const val SAVED_STR = ""
    }
}
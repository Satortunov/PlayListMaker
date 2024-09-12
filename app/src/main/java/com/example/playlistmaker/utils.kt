package com.example.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import com.example.playlistmaker.R


const val SAVED_TRACK = "savedtrack"


const val SAVED_STRING = "SAVED_STRING"
const val SAVED_STR = ""

const val CLICK_DEBOUNCE_DELAY = 1000L
const val SEARCH_DEBOUNCE_DELAY = 2000L
const val DELAY_MILLIS = 25L

const val STATE_DEFAULT = 0
const val STATE_PREPARED = 1
const val STATE_PLAYING = 2
const val STATE_PAUSED = 3

const val TIME_FORMAT = "mm:ss"
const val START_TIME = "00:00"


var isClickAllowed = true
val handler = Handler(Looper.getMainLooper())

    fun Context.dpToPx(dp: Int): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics)

    fun clickDebounce() : Boolean {
         val current = isClickAllowed
         if (isClickAllowed) {
             isClickAllowed = false
             handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY.toLong())
         }
     return current
    }









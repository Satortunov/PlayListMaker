package com.example.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.TypedValue


const val SAVED_TRACK = "savedtrack"
const val CLICK_DEBOUNCE_DELAY = 1000

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










package com.example.utils

import android.content.Context
import android.util.TypedValue

    const val SAVED_TRACK = "savedtrack"

    fun Context.dpToPx(dp: Int): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics)








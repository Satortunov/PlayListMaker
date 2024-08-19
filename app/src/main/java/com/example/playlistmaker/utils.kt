package com.example.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun Context.dpToPx(dp: Int): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics)

fun Context.loadImageFromInternet(context: View, from: String, placeHolder: Drawable, radius: Int, intoImageView: ImageView)
{
    Glide.with(context)
        .load(from)
        .placeholder(placeHolder)
        .centerCrop()
        .transform(RoundedCorners(dpToPx(resources.getDimensionPixelSize(radius)).toInt()))
        .into(intoImageView)

}
package com.example.playlistmaker

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.utils.*


class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val trackName = itemView.findViewById<TextView>(R.id.trackName)
    private val artistName = itemView.findViewById<TextView>(R.id.artistName)
    private val trackTime = itemView.findViewById<TextView>(R.id.trackTime)
    private val artworkUrl = itemView.findViewById<ImageView>(R.id.artworkUrl)

    fun Context.loadImageFromInternet(context: Context, from: String, placeHolder: Drawable, radius: Int, intoImageView: ImageView)
    {
        Glide.with(context)
            .load(from)
            .placeholder(placeHolder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(resources.getDimensionPixelSize(radius)).toInt()))
            .into(intoImageView)

    }

    fun bind(track: Track) {
        trackName.text = track.trackName
        artistName.text = track.artistName
        trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)

         /*Glide.with(itemView)
            .load(item.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(itemView.context.dpToPx(itemView.resources.getDimensionPixelSize(R.dimen.size_dp_2)).toInt()))
            .into(artworkUrl)*/
    }
}
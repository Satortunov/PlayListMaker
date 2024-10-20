package com.example.playlistmaker.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.utils.*

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val trackName = itemView.findViewById<TextView>(R.id.trackName)
    private val artistName = itemView.findViewById<TextView>(R.id.artistName)
    private val trackTime = itemView.findViewById<TextView>(R.id.trackTime)
    private val artworkUrl = itemView.findViewById<ImageView>(R.id.artworkUrl)

    fun bind(track: Track?) {
        if (track?.trackName != null) trackName.text = track.trackName
        else trackName.text = R.string.no_name.toString()

        if (track?.artistName != null) artistName.text = track.artistName
        else artistName.text = R.string.unknown_musician.toString()

        if (track?.trackTimeMillis != null) trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        else trackTime.text = R.string.no_data.toString()

        Glide.with(itemView)
            .load(track?.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(itemView.context.dpToPx(itemView.resources.getDimensionPixelSize(
                R.dimen.size_dp_2
            )).toInt()))
            .into(artworkUrl)
    }
}
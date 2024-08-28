package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter(private val tracks: ArrayList<Track?>, val clickTrackListener: PressTrackListener) :

    RecyclerView.Adapter<SearchViewHolder>()  {

    fun interface PressTrackListener {
          fun onTrackClick(track: Track?)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return SearchViewHolder(view)
    }
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(tracks.get(position))
        holder.itemView.setOnClickListener { it -> clickTrackListener.onTrackClick(tracks[position]) }
    }

    override fun getItemCount() = tracks.size
}

package com.example.playlistmaker

import com.example.playlistmaker.Track

data class TrackResponse(
    val resultCount: Int,
    val results: List<Track>
)
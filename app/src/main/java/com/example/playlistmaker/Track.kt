package com.example.playlistmaker

import java.io.Serializable

data class Track (
        val trackName: String,
        val artistName: String,
        val trackTimeMillis: Long,
        val artworkUrl100: String,
        val collectionName: String,
        val releaseDate: String,
        val primaryGenreName: String,
        val country: String,
        val trackId: Int,
        val previewUrl: String
) : Serializable {}


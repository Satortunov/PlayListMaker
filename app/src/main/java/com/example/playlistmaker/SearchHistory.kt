package com.example.playlistmaker

import android.content.SharedPreferences
import com.example.playlistmaker.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


const val TRACKS_LIST_KEY = "track_key"

class SearchHistory {

    val maxTracks = 10

    fun readTrackList(sharedPreferences: SharedPreferences): ArrayList<Track> {
        val json = sharedPreferences.getString(TRACKS_LIST_KEY, null) ?: return ArrayList()
        val tracksList: Type = object : TypeToken<ArrayList<Track>?>() {}.type
        return Gson().fromJson(json, tracksList)
    }

    fun writeTrackList(sharedPreferences: SharedPreferences, tracks: ArrayList<Track>) {
        val json = Gson().toJson(tracks)
        sharedPreferences.edit()
            .putString(TRACKS_LIST_KEY, json)
            .apply()
    }

    fun clearAllTracks(sharedPreferences: SharedPreferences) {
        sharedPreferences.edit()
            .remove(TRACKS_LIST_KEY)
            .apply()
    }

    fun setTrack(track: Track, sharedPreferences: SharedPreferences) {
        val tracks = readTrackList(sharedPreferences)
        if (!tracks.remove(track) && tracks.size >= maxTracks) tracks.removeAt(maxTracks - 1)
        tracks.add(0, track)
        writeTrackList(sharedPreferences, tracks)
    }

}
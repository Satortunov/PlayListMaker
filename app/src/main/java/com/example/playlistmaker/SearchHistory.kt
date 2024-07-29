package com.example.playlistmaker

import android.content.SharedPreferences
import com.example.playlistmaker.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

const val TRACKS_LIST_KEY = "track_key"
const val MAX_TRACKS = 10

class SearchHistory {

    fun readTrackList(sharedPreferences: SharedPreferences):  ArrayList<Track> {
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

    fun setTrackList(sharedPreferences: SharedPreferences, track: Track) {
        val tracks = readTrackList(sharedPreferences)
        if (!tracks.remove(track) && tracks.size >= MAX_TRACKS) tracks.removeAt(MAX_TRACKS - 1)
        tracks.add(0, track)
        writeTrackList(sharedPreferences, tracks)
    }

}
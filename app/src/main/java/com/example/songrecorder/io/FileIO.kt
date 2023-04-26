package com.example.songrecorder.io

import android.os.Environment
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.io.File
import java.nio.file.Files

object FileIO {
    val MAIN_APP_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath  + "/" + "SongRecorder"

    // Create a SongRecorder directory
    fun createMainAppDirectory() {
        File(MAIN_APP_DIRECTORY).mkdir()
    }

    fun updateListOfSongFolders(songTitles: SnapshotStateList<String>) {
        songTitles.clear()
        val songTitlesDirectories = File(MAIN_APP_DIRECTORY).list()
        songTitlesDirectories.sortWith(String.CASE_INSENSITIVE_ORDER)
        for (directory in songTitlesDirectories) run {
            songTitles.add(directory)
        }
    }

    fun createNewSongDirectory(songTitle: String, songTitles: SnapshotStateList<String>) {
        if (!songDirectoryExists(songTitle)) {
            File(MAIN_APP_DIRECTORY + "/" + songTitle).mkdir()
            updateListOfSongFolders(songTitles)
        }
    }

    fun songDirectoryExists(directoryName : String): Boolean {
        return File(MAIN_APP_DIRECTORY).list().contains(directoryName)
    }
}
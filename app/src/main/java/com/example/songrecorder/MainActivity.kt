package com.example.songrecorder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.songrecorder.io.FileIO
import com.example.songrecorder.ui.component.insertNewSongDialog
import com.example.songrecorder.ui.component.showSongEditPaneTextField
import com.example.songrecorder.ui.component.showSongList
import com.example.songrecorder.ui.theme.SongRecorderTheme

class MainActivity : ComponentActivity() {
    // onCreate is the entry point for the application
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FileIO.createMainAppDirectory()
        setContent {
            SongRecorderTheme {
                val globalPaneTitle = remember { mutableStateOf("Songs") }
                val songTitles = remember { mutableStateListOf<String>()}
                val showSongListPane = remember { mutableStateOf(true) }
                val showSongEditPane = remember { mutableStateOf(false) }

                showGlobalPaneTitle(globalPaneTitle)
                // Song List Pane
                showSongList(songTitles, showSongListPane, showSongEditPane, globalPaneTitle)
                insertNewSongDialog(songTitles, showSongListPane)

                // Song Edit Pane
                showSongEditPaneTextField(showSongEditPane, showSongListPane)
            }
        }
    }
}

@Composable
fun showGlobalPaneTitle(globalPaneTitle: MutableState<String>) {
    Text(globalPaneTitle.value, fontWeight = FontWeight.Bold, fontSize = 30.sp, modifier = Modifier.padding(20.dp))
    Divider(color=Color.DarkGray, modifier = Modifier
        .height(140.dp)
        .padding(vertical = 68.dp))
}
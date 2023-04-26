package com.example.songrecorder.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.songrecorder.io.FileIO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun insertNewSongDialog(songTitles: SnapshotStateList<String>, showSongListPane: MutableState<Boolean>) {
    if (showSongListPane.value) {
        val newSongDialogText = remember { mutableStateOf("") }
        val newSongDialogTextLength = remember { mutableStateOf(0) }
        val newSongDialogIsVisible = remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Button(onClick = {
                newSongDialogIsVisible.value = true
            }, modifier = Modifier.padding(14.dp))
            { Text("+ New Song", modifier = Modifier.padding(10.dp)) }
        }

        if (newSongDialogIsVisible.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    newSongDialogIsVisible.value = false
                    newSongDialogText.value = ""
                },
                title = {
                    Text(text = "Insert new song name")
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            value = newSongDialogText.value,
                            singleLine = true,
                            onValueChange = {
                                if (it.length <= 50) {
                                    newSongDialogTextLength.value = it.length
                                    newSongDialogText.value = it
                                }
                            },
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .background(Color.Transparent)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            newSongDialogIsVisible.value = false
                            FileIO.createNewSongDirectory(newSongDialogText.value, songTitles)
                            FileIO.updateListOfSongFolders(songTitles)
                            newSongDialogText.value = ""
                        }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            newSongDialogIsVisible.value = false
                            newSongDialogText.value = ""
                        }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun showSongList(songTitles: SnapshotStateList<String>,
                 showSongListPane: MutableState<Boolean>,
                 showSongEditPane: MutableState<Boolean>,
                 globalPaneTitle: MutableState<String>) {
    if (showSongListPane.value) {
        FileIO.updateListOfSongFolders(songTitles)
        globalPaneTitle.value = "Songs"
        SongListLayout {
            Spacer(modifier = Modifier.weight(1f))
            LazyColumn(Modifier.weight(10f)) {
                items(songTitles) { songTitle ->
                    Text(songTitle, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clickable {
                            showSongListPane.value = false
                            showSongEditPane.value = true
                            globalPaneTitle.value = songTitle
                        }
                        .padding(24.dp))
                    Divider(modifier = Modifier.height(3.dp))
                }
            }
        }
    }
}

@Composable
fun SongListLayout(content: @Composable ColumnScope.()->Unit){
    Column {
        content()
    }
}

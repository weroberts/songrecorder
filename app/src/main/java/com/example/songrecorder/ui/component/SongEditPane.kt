package com.example.songrecorder.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showSongEditPaneTextField(showSongEditPane: MutableState<Boolean>,
                              showSongListPane: MutableState<Boolean>) {
    if (showSongEditPane.value) {
        val songEditPaneTextField = rememberSaveable { mutableStateOf("") }

        BackHandler(enabled = true, onBack = {
            showSongEditPane.value = false
            showSongListPane.value = true
        })
        TextField(
            value = songEditPaneTextField.value,
            onValueChange = {
                songEditPaneTextField.value = it
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp, bottom = 300.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
        )
    }
}
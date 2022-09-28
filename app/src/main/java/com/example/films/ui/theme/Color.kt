package com.example.films.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

@Composable
fun primaryText():Color {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    return if (isSystemInDarkTheme){
        Color(0xFF998686)
    }else {
        Color(0xFF201D1D)
    }
}

val primaryBackground = Color(0xFFFFFFFF)
val secondaryBackground = Color(0xFFF3F4F5)
val tintColor = Color(0xFF00E7FC)
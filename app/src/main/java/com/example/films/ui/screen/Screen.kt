package com.example.films.ui.screen

sealed class Screen(val route:String) {
    object HomeScreen:Screen("home_screen")
}
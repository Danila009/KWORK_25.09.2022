@file:OptIn(ExperimentalAnimationApi::class)

package com.example.films.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.films.ui.screen.Screen
import com.example.films.ui.screen.homeScreen.HomeScreen
import com.example.films.ui.view.CustomBottomNavigation

@Composable
fun BaseNavHost(
    navController: NavHostController = rememberNavController()
) {
    var bottomBarId by remember { mutableStateOf(Screen.HomeScreen.route) }

    Scaffold(
        bottomBar = {
            CustomBottomNavigation(
                navController = navController,
                currentScreenId = bottomBarId,
                onItemSelected = {
                    bottomBarId = it.id
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route
        ){
            composable(Screen.HomeScreen.route){
                HomeScreen(
                    navController = navController
                )
            }
        }
    }
}
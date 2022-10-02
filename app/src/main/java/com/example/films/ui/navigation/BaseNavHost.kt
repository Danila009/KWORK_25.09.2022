package com.example.films.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.films.ui.screen.filmInfoScreen.FilmInfoScreen
import com.example.films.ui.screen.Screen
import com.example.films.ui.screen.addAdminScreen.AddAdminScreen
import com.example.films.ui.screen.createAdvertisingScreen.CreateAdvertisingScreen
import com.example.films.ui.screen.editingAdvertisingScreen.EditingAdvertisingScreen
import com.example.films.ui.screen.homeScreen.HomeScreen
import com.example.films.ui.screen.profileScreen.ProfileScreen
import com.example.films.ui.screen.watchMovieScreen.WatchMovieScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BaseNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ){
        composable(Screen.HomeScreen.route){
            HomeScreen(
                navController = navHostController
            )
        }

        composable(Screen.ProfileScreen.route){
            ProfileScreen(
                navController = navHostController
            )
        }

        composable(
            route = Screen.WatchMovieScreen.route,
            arguments = listOf(
                navArgument("url"){
                    type = NavType.StringType
                }
            )
        ){
            WatchMovieScreen(
                navComposable = navHostController,
                url = it.arguments!!.getString("url","")
            )
        }

        composable(Screen.CreateAdvertising.route){
            CreateAdvertisingScreen(
                navController = navHostController
            )
        }

        composable(Screen.AddAdminScreen.route){
            AddAdminScreen(
                navController = navHostController
            )
        }

        composable(Screen.EditingAdvertisingScreen.route){
            EditingAdvertisingScreen(
                navController = navHostController
            )
        }

        composable(
            route = Screen.FilmInfoScreen.route,
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                },
                navArgument("kinopoiskId"){
                    type = NavType.StringType
                },
                navArgument("imdbId"){
                    type = NavType.StringType
                }
            )
        ){
            FilmInfoScreen(
                navController = navHostController,
                id = it.arguments!!.getInt("id"),
                kinopoiskId = it.arguments!!.getString("kinopoiskId",""),
                imdbId = it.arguments!!.getString("imdbId","")
            )
        }
    }
}
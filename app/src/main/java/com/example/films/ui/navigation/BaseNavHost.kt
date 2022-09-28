@file:OptIn(ExperimentalAnimationApi::class)

package com.example.films.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.films.ui.screen.filmInfoScreen.FilmInfoScreen
import com.example.films.ui.screen.Screen
import com.example.films.ui.screen.homeScreen.HomeScreen
import com.example.films.ui.screen.profileScreen.ProfileScreen
import com.example.films.ui.screen.watchMovieScreen.WatchMovieScreen
import com.example.films.ui.theme.primaryBackground
import com.example.films.ui.view.CustomBottomNavigation

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BaseNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    var bottomBarId by remember { mutableStateOf(Screen.HomeScreen.route) }

    val route = navHostController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (route != Screen.WatchMovieScreen.route){
                CustomBottomNavigation(
                    navController = navHostController,
                    currentScreenId = bottomBarId,
                    onItemSelected = {
                        bottomBarId = it.id
                    }
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = primaryBackground
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
    }
}
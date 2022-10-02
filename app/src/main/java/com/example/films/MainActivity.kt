package com.example.films

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.films.ui.navigation.BaseNavHost
import com.example.films.ui.screen.Screen
import com.example.films.ui.theme.FilmsTheme
import com.example.films.ui.theme.primaryBackground
import com.example.films.ui.view.CustomBottomNavigation
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmsTheme {
                val navHostController = rememberNavController()

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
                        BaseNavHost(
                            navHostController = navHostController
                        )
                    }
                }
            }
        }
    }
}
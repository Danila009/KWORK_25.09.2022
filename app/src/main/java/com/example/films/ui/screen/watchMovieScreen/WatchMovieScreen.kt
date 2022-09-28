package com.example.films.ui.screen.watchMovieScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WatchMovieScreen(
    navComposable: NavController,
    viewModel: WatchMovieViewModel = hiltViewModel(),
    url: String
) {
    val activity = LocalContext.current as Activity

    val state = rememberWebViewState(url)

    WebView(
        modifier = Modifier.fillMaxSize(),
        state = state,
        captureBackPresses = true,
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true

            activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        },
        onDispose = { webView ->
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    )
}
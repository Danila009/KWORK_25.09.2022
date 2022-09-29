package com.example.films.ui.screen.profileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.films.ui.theme.primaryBackground

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val token = viewModel.getToken

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {

        if (token == null){
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                authView(
                    navController = navController,
                    viewModel = viewModel
                )

                item {
                    Spacer(modifier = Modifier.heightIn(50.dp))
                }
            }
        }else {
            LazyColumn {
                userView(
                    navController = navController,
                    viewModel = viewModel
                )

                item {
                    Spacer(modifier = Modifier.heightIn(50.dp))
                }
            }
        }
    }
}
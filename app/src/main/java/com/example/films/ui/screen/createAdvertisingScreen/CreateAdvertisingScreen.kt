package com.example.films.ui.screen.createAdvertisingScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.films.data.network.model.AdvertisingCreate
import com.example.films.data.network.utils.Result
import com.example.films.ui.screen.Screen
import com.example.films.ui.theme.primaryBackground
import com.example.films.ui.theme.tintColor
import com.example.films.ui.view.TextFieldBase
import com.example.films.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun CreateAdvertisingScreen(
    navController: NavController,
    viewModel: CreateAdvertisingViewModel = hiltViewModel(),
) {
    var title by remember { mutableStateOf("") }
    var webUrl by remember { mutableStateOf("") }

    var error by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    viewModel.responseCreateAdvertisingResponse.onEach { result ->
        when(result){
            is Result.Error -> {
                error = result.message ?: "Error"
                loading = false
            }
            is Result.Loading -> {
                error = ""
                loading = true
            }
            is Result.Success -> {
                navController.navigate(Screen.ProfileScreen.route)
            }
        }
    }.launchWhenStarted()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {

                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.W900
                )

                if (loading){
                    CircularProgressIndicator(
                        color = primaryBackground,
                        modifier = Modifier.padding(5.dp)
                    )
                }

                TextFieldBase(
                    value = title,
                    onValueChange = { title = it },
                    label = "Название"
                )

                TextFieldBase(
                    value = webUrl,
                    onValueChange = { webUrl = it },
                    label = "Ссылка на рекламу"
                )

                Button(
                    modifier = Modifier.padding(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = tintColor
                    ),
                    shape = AbsoluteRoundedCornerShape(10.dp),
                    onClick = {
                        viewModel.createAdvertising(
                            body = AdvertisingCreate(
                                title = title,
                                webUrl = webUrl
                            )
                        )
                    }
                ) {
                    Text(text = "Добавить рекламу")
                }
            }

            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}
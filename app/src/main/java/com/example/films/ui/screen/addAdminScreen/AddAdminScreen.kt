package com.example.films.ui.screen.addAdminScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.films.data.network.model.User
import com.example.films.data.network.utils.Result
import com.example.films.ui.screen.Screen
import com.example.films.ui.theme.primaryBackground
import com.example.films.ui.theme.primaryText
import com.example.films.ui.theme.tintColor
import com.example.films.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun AddAdminScreen(
    navController: NavController,
    viewModel: AddAdminViewModel = hiltViewModel(),
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    var users by remember { mutableStateOf<Result<List<User>>>(Result.Loading()) }

    viewModel.responseUser.onEach { result ->
        users = result
    }.launchWhenStarted()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getUsers()
    })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        LazyColumn {
            when(users){
                is Result.Error -> {
                    item {
                        Column(
                            modifier = Modifier.size(
                                width = screenWidthDp,
                                height = screenHeightDp
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = users.message ?: "Error",
                                fontWeight = FontWeight.W900,
                                modifier = Modifier.padding(5.dp),
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                is Result.Loading -> {
                    item {
                        Column(
                            modifier = Modifier.size(
                                width = screenWidthDp,
                                height = screenHeightDp
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                color = tintColor
                            )
                        }
                    }
                }
                is Result.Success -> {
                    items(users.data!!){ user ->

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "имя ${user.username}",
                                    fontWeight = FontWeight.W900,
                                    modifier = Modifier.padding(5.dp),
                                    color = primaryText()
                                )

                                Text(
                                    text = "логин ${user.login}",
                                    modifier = Modifier.padding(5.dp),
                                    color = primaryText()
                                )
                            }

                            Button(
                                modifier = Modifier.padding(5.dp),
                                shape = AbsoluteRoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = primaryBackground
                                ),
                                onClick = {
                                    viewModel.postAdmin(userId = user.id)
                                    navController.navigate(Screen.ProfileScreen.route)
                                }
                            ) {
                                Text(text = "Добавить админа")
                            }
                        }

                        Divider()
                    }
                }
            }
        }
    }
}
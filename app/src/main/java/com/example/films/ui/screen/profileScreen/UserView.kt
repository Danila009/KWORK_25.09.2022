package com.example.films.ui.screen.profileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.films.data.network.model.User
import com.example.films.data.network.model.UserRole
import com.example.films.data.network.utils.Result
import com.example.films.ui.screen.Screen
import com.example.films.ui.theme.primaryText
import com.example.films.ui.theme.tintColor
import com.example.films.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
fun LazyListScope.userView(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    item {

        var user by remember { mutableStateOf<Result<User>>(Result.Loading()) }

        val userRole = viewModel.getUserRole

        viewModel.responseUser.onEach {
            user = it
        }.launchWhenStarted()

        LaunchedEffect(key1 = Unit, block = {
            viewModel.getUser()
        })

        when(user){
            is Result.Error -> {
                Text(
                    text = user.message ?: "Error",
                    color = Color.Red,
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.W900
                )
            }
            is Result.Loading -> {
                CircularProgressIndicator(
                    color = tintColor
                )
            }
            is Result.Success -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = user.data!!.username,
                            color = primaryText(),
                            fontWeight = FontWeight.W900,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    Column {
                        if (!user.data!!.subscription){
                            Button(
                                modifier = Modifier.padding(5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = tintColor
                                ),
                                shape = AbsoluteRoundedCornerShape(10.dp),
                                onClick = { /*TODO*/ }
                            ) {
                                Text(
                                    text = "Отключить реклама",
                                    color = Color.White
                                )
                            }
                        }

                        if (userRole == UserRole.ADMIN_USER){
                            Button(
                                modifier = Modifier.padding(5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = tintColor
                                ),
                                shape = AbsoluteRoundedCornerShape(10.dp),
                                onClick = {
                                    navController.navigate(Screen.CreateAdvertising.route)
                                }
                            ) {
                                Text(
                                    text = "Добавить рекламу",
                                    color = Color.White
                                )
                            }

                            Button(
                                modifier = Modifier.padding(5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = tintColor
                                ),
                                shape = AbsoluteRoundedCornerShape(10.dp),
                                onClick = {
                                    navController.navigate(Screen.AddAdminScreen.route)
                                }
                            ) {
                                Text(
                                    text = "Добавить админа",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
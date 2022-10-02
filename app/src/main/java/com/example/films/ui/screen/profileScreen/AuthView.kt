package com.example.films.ui.screen.profileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.films.data.network.model.AuthorizationBody
import com.example.films.data.network.model.RegistrationBody
import com.example.films.data.network.model.UserRole
import com.example.films.data.network.utils.Result
import com.example.films.ui.screen.Screen
import com.example.films.ui.theme.tintColor
import com.example.films.ui.view.TextFieldBase
import com.example.films.ui.view.TextFieldPassword
import com.example.films.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
fun LazyListScope.authView(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    item {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var login by remember { mutableStateOf("") }

        var error by remember { mutableStateOf("") }

        viewModel.responseRegistrationResponse.onEach { result ->
            when(result){
                is Result.Error -> {
                    viewModel.authorizationClear()
                    error = result.message ?: ""
                }
                is Result.Loading -> {
                    viewModel.authorizationClear()
                    error = "Loading..."
                }
                is Result.Success -> {
                    viewModel.authorizationClear()
                    if (result.data?.error == null){
                        viewModel.authorization(body = AuthorizationBody(
                            login = login,
                            password = password
                        ))
                    }else {
                        error = result.data.error
                    }
                }
                null -> Unit
            }
        }.launchWhenStarted()

        viewModel.responseAuthorizationResponse.onEach { result ->
            when(result){
                is Result.Error -> {
                    viewModel.registrationClear()
                    error = result.message ?: ""
                }
                is Result.Loading -> {
                    viewModel.registrationClear()
                    error = "Loading..."
                }
                is Result.Success -> {
                    viewModel.registrationClear()
                    viewModel.saveToken(result.data?.access_token)
                    viewModel.savePassword(password)
                    viewModel.saveLogin(login)
                    viewModel.saveUserRole(result.data?.role ?: UserRole.BASE_USER)
                    navController.navigate(Screen.ProfileScreen.route)
                }
                null -> Unit
            }
        }.launchWhenStarted()

        Text(
            text = error,
            color = Color.Red,
            fontWeight = FontWeight.W900,
            fontSize = 20.sp,
            modifier = Modifier.padding(5.dp),
            textAlign = TextAlign.Center
        )

        TextFieldBase(
            value = username,
            onValueChange = { username = it },
            label = "Имя пользователя"
        )

        TextFieldBase(
            value = login,
            onValueChange = { login = it },
            label = "Логин"
        )

        TextFieldPassword(
            value = password,
            onValueChange = { password = it },
            label = "Пароль"
        )

        Button(
            modifier = Modifier.padding(5.dp),
            shape = AbsoluteRoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = tintColor
            ),
            onClick = {
                viewModel.authorization(body = AuthorizationBody(
                    login = login,
                    password = password
                ))
            }
        ) {
            Text(
                text = "Авторизация",
                color = Color.White
            )
        }

        Button(
            modifier = Modifier.padding(5.dp),
            shape = AbsoluteRoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = tintColor
            ),
            onClick = {
                viewModel.registration(body = RegistrationBody(
                    username = username,
                    login = login,
                    password = password
                ))
            }
        ) {
            Text(
                text = "Регистрация",
                color = Color.White
            )
        }
    }
}
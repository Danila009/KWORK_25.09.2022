package com.example.films.ui.screen.profileScreen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.films.data.network.model.FreekassaData
import com.example.films.data.network.model.User
import com.example.films.data.network.model.UserRole
import com.example.films.data.network.subscription.model.Subscription
import com.example.films.data.network.utils.Result
import com.example.films.ui.screen.Screen
import com.example.films.ui.theme.primaryText
import com.example.films.ui.theme.tintColor
import com.example.films.ui.view.OnLifecycleEvent
import com.example.films.utils.Freekassa
import com.example.films.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("FlowOperatorInvokedInComposition", "NewApi")
fun LazyListScope.userView(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    item {
        val context = LocalContext.current

        val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
        val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

        val scope = rememberCoroutineScope()

        val freekassa = Freekassa()

        var user by remember { mutableStateOf<Result<User>>(Result.Loading()) }
        val userRole = viewModel.getUserRole

        var freekassaData by remember { mutableStateOf<Result<FreekassaData>>(Result.Loading()) }
        var subscriptionMain by remember { mutableStateOf<Result<Subscription>>(Result.Loading()) }

        var orderNumber by remember { mutableStateOf("") }

        viewModel.responseUser.onEach {
            user = it
        }.launchWhenStarted()

        viewModel.responseSubscriptionMain.onEach { result ->
            subscriptionMain = result
        }.launchWhenStarted()

        viewModel.responseFreekassaData.onEach { result ->
            freekassaData = result

            when(result){
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> {
                    subscriptionMain.data ?: return@onEach
                    orderNumber = UUID.randomUUID().toString()
                    freekassa.payBrowser(
                        context = context,
                        shopId = result.data!!.shopId,
                        secretWordOne = result.data.secretWordOne,
                        orderNumber = orderNumber,
                        price = subscriptionMain.data!!.price
                    )
                }
            }
        }.launchWhenStarted()

        LaunchedEffect(key1 = Unit, block = {
            viewModel.getUser()
            viewModel.getSubscriptionMain()
        })

        OnLifecycleEvent { owner, event ->
            if(event == Lifecycle.Event.ON_RESUME){
                scope.launch {
                    when(freekassaData){
                        is Result.Error -> Unit
                        is Result.Loading -> Unit
                        is Result.Success -> {
                            subscriptionMain.data ?: return@launch
                            val freekassaOrderBody = freekassa.createFreekassaOrderBody(
                                cashKey = freekassaData.data?.cashKey ?: "",
                                orderNumber = orderNumber,
                                shopId = subscriptionMain.data!!.shopId,
                            )
                            viewModel.getFreekassaByShopId(shopId = 0)

                            when(val subscriptionResult = viewModel.getOrders(body = freekassaOrderBody)){
                                is Result.Error -> {
                                    Toast.makeText(
                                        context,
                                        subscriptionResult.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                is Result.Loading -> Unit
                                is Result.Success -> {
                                    if ((subscriptionResult.data?.orders ?: emptyList()).isNotEmpty()){
                                        viewModel.patchSubscription(subscription = true)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

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
            is Result.Success -> {

                Text(
                    text = user.data!!.username,
                    color = primaryText(),
                    fontWeight = FontWeight.W900,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )

                Text(
                    text = user.data!!.login,
                    color = primaryText(),
                    fontWeight = FontWeight.W100,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (!user.data!!.subscription){
                        Button(
                            modifier = Modifier.padding(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = tintColor
                            ),
                            shape = AbsoluteRoundedCornerShape(10.dp),
                            onClick = {
                                subscriptionMain.data ?: return@Button
                                viewModel.getFreekassaByShopId(shopId = subscriptionMain.data!!.shopId)
                            }
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
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (userRole == UserRole.ADMIN_USER){

                        Button(
                            modifier = Modifier.padding(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = tintColor
                            ),
                            shape = AbsoluteRoundedCornerShape(10.dp),
                            onClick = {
                                navController.navigate(Screen.EditingAdvertisingScreen.route)
                            }
                        ) {
                            Text(
                                text = "Редактировать рекламу",
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
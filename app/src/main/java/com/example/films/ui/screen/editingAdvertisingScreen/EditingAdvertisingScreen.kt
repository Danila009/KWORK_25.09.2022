package com.example.films.ui.screen.editingAdvertisingScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.films.data.network.model.Advertising
import com.example.films.data.network.utils.Result
import com.example.films.ui.screen.Screen
import com.example.films.ui.theme.primaryBackground
import com.example.films.ui.theme.tintColor
import com.example.films.ui.view.Image
import com.example.films.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun EditingAdvertisingScreen(
    navController: NavController,
    viewModel: EditingAdvertisingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    var advertising by remember { mutableStateOf<Result<List<Advertising>>>(Result.Loading()) }

    viewModel.responseAdvertising.onEach { result ->
        advertising = result
    }.launchWhenStarted()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAdvertising()
    })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        LazyColumn {
            when(advertising){
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
                                text = advertising.message ?: "Error",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.W900,
                                color = Color.Red
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
                    items(advertising.data ?: emptyList()){ item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = item.title,
                                    fontWeight = FontWeight.W900,
                                    modifier = Modifier.padding(5.dp)
                                )

                                Text(
                                    text = item.webUrl,
                                    fontWeight = FontWeight.W900,
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .clickable {
                                            context.startActivity(
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse(item.webUrl)
                                                )
                                            )
                                        }
                                )

                                item.imageUrl?.let {
                                    Image(
                                        url = item.imageUrl,
                                        progressIndicator = false,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(max = 150.dp)
                                            .padding(5.dp)
                                    )
                                }
                            }

                            Button(
                                modifier = Modifier.padding(5.dp),
                                shape = AbsoluteRoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = primaryBackground
                                ),
                                onClick = {
                                    viewModel.deleteAdvertising(id = item.id)
                                    navController.navigate(Screen.ProfileScreen.route)
                                }
                            ) {
                                Text(text = "Удалить")
                            }
                        }

                        Divider()
                    }
                }
            }
        }
    }
}
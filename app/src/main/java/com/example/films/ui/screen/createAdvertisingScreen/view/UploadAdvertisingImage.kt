package com.example.films.ui.screen.createAdvertisingScreen.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.films.ui.theme.secondaryBackground
import com.example.films.ui.theme.tintColor

@Composable
fun UploadAdvertisingImageAlertDialog(
    onDismissRequest:() -> Unit = {},
    onUploadImage:() -> Unit = {},
    onSkip:() -> Unit = {}
) {
    var loading by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        shape = AbsoluteRoundedCornerShape(20.dp),
        backgroundColor = secondaryBackground,
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if (loading){
                    CircularProgressIndicator(
                        color = tintColor,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.padding(5.dp).width(100.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = tintColor
                    ),
                    shape = AbsoluteRoundedCornerShape(10.dp),
                    onClick = { onSkip() }
                ) {
                    Text(
                        text = "Пропустить",
                        color = Color.White
                    )
                }

                Button(
                    modifier = Modifier.padding(5.dp).width(100.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = tintColor
                    ),
                    shape = AbsoluteRoundedCornerShape(10.dp),
                    onClick = {
                        loading = true
                        onUploadImage()
                    }
                ) {
                    Text(
                        text = "Загрузить картинку",
                        color = Color.White
                    )
                }
            }
        }
    )
}
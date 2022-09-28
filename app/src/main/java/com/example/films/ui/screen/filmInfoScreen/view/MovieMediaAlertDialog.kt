package com.example.films.ui.screen.filmInfoScreen.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.films.data.network.model.MovieMedia
import com.example.films.ui.theme.primaryText
import com.example.films.ui.theme.secondaryBackground

@Composable
fun MovieMediaAlertDialog(
    media:List<MovieMedia> = emptyList(),
    onDismissRequest:() -> Unit = {},
    onClickTranslation:(MovieMedia) -> Unit = {}
) {
    AlertDialog(
        backgroundColor = secondaryBackground,
        shape = AbsoluteRoundedCornerShape(20.dp),
        onDismissRequest = onDismissRequest,
        title = {

        },
        text = {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Text(
                        text = "Выбирите озвучку",
                        fontWeight = FontWeight.W900,
                        modifier = Modifier.padding(10.dp),
                        color = primaryText()
                    )
                }

                items(media){ item ->
                    TextButton(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        onClick = { onClickTranslation(item) }
                    ) {
                        Text(
                            text = item.translation.title,
                            color = primaryText(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        },
        buttons = {

        }
    )
}
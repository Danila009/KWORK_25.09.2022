package com.example.films.ui.view

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.films.ui.theme.tintColor

@Composable
fun Image(
    url:Any?,
    modifier: Modifier,
    contentDescription:String? = null,
    progressIndicator:Boolean = true
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier
    ) {
        val state = painter.state
        if (
            state is AsyncImagePainter.State.Loading ||
            state is AsyncImagePainter.State.Error
        ) {
            if (progressIndicator){
                CircularProgressIndicator(
                    color = tintColor
                )
            }
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}
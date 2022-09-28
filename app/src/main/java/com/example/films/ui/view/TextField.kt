package com.example.films.ui.view

import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.films.ui.theme.primaryText
import com.example.films.ui.theme.secondaryBackground
import com.example.films.ui.theme.tintColor

@Composable
fun TextFieldSearch(
    modifier: Modifier = Modifier,
    value: String,
    placeholder:String = "Search",
    onValueChange: (String) -> Unit,
    onClose:() -> Unit = {}
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        shape = AbsoluteRoundedCornerShape(5.dp),
        placeholder = {
            Text(
                text = placeholder,
                color = primaryText()
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = tintColor
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()){
                IconButton(onClick = {
                    onClose()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = tintColor
                    )
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = primaryText(),
            backgroundColor = secondaryBackground,
            cursorColor = primaryText(),
            focusedIndicatorColor = secondaryBackground
        ), keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ), modifier = modifier
            .clip(AbsoluteRoundedCornerShape(15.dp))
    )
}
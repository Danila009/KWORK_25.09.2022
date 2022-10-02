package com.example.films.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.films.ui.screen.Screen
import com.example.films.ui.theme.primaryBackground
import com.example.films.ui.theme.primaryText
import com.example.films.ui.theme.secondaryBackground
import com.example.films.ui.theme.tintColor

@ExperimentalAnimationApi
@Composable
fun CustomBottomNavigation(
    navController:NavController,
    currentScreenId:String,
    backgroundColor:Color = secondaryBackground,
    onItemSelected:(ScreenBottomNavigation)->Unit
) {

    val items = ScreenBottomNavigation.Items.list

    Row(
        modifier= Modifier
            .padding(8.dp)
            .padding(horizontal = 70.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clip(AbsoluteRoundedCornerShape(15.dp))
            .background(backgroundColor),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        items.forEach { item->
            CustomBottomNavigationItem(
                item = item,
                isSelected = item.id == currentScreenId
            ) {
                onItemSelected(item)

                when(item){
                    ScreenBottomNavigation.Home -> navController.navigate(Screen.HomeScreen.route)
                    ScreenBottomNavigation.Profile -> navController.navigate(Screen.ProfileScreen.route)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun CustomBottomNavigationItem(
    item:ScreenBottomNavigation,
    isSelected:Boolean,
    onClick:()->Unit
){

    val background = if (isSelected) tintColor.copy(alpha = 0.1f) else Color.Transparent
    val contentColor = if (isSelected) tintColor else primaryText()

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
    ){
        Row(
            modifier=Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = contentColor
            )

            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = item.title,
                    color = contentColor,
                )
            }
        }
    }
}


sealed class ScreenBottomNavigation(
    val id:String,
    val title:String,
    val icon: ImageVector,
){
    object Home:ScreenBottomNavigation(Screen.HomeScreen.route,"Дом", Icons.Outlined.Home)
    object Profile:ScreenBottomNavigation(Screen.ProfileScreen.route,"Пользователь", Icons.Outlined.Person)

    object Items{
        val list= listOf(
            Home,Profile
        )
    }

}
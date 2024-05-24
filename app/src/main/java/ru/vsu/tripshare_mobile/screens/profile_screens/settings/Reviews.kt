package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.ui.theme.darkGray24
import ru.vsu.tripshare_mobile.ui.theme.mint24

@Composable
fun Reviews(user: UserModel, person: UserModel, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Отзывы о пользователе",
            style = mint24
        )

        if(user.reviews == null){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "У пользователя пока нет оценок", style = darkGray24)
            }
        }else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
            ) {
                itemsIndexed(user.reviews!!) { _, item ->
                    Review(item, navController)
                }
            }
        }
    }
}
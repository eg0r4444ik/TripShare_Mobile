package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.ReviewModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.services.ReviewService
import ru.vsu.tripshare_mobile.services.UserService
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.darkGray18
import ru.vsu.tripshare_mobile.ui.theme.mint24
import ru.vsu.tripshare_mobile.ui.theme.white18

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun Reviews(userId: Int, navController: NavController){

    var reviews by remember { mutableStateOf(emptyList<ReviewModel>()) }
    var user by remember { mutableStateOf<UserModel?>(null) }

    LaunchedEffect(Unit) {
        val reviewsResult = ReviewService.getUsersReviews(userId)
        val userResult = UserService.getUser(userId)
        if(reviewsResult.isSuccess && userResult.isSuccess) {
            reviews = reviewsResult.getOrElse { emptyList() }
            user = userResult.getOrNull()
        }
    }

    Scaffold(
        content = {
            if(user != null) {
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

                    if (reviews.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f).padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "У пользователя пока нет оценок", style = darkGray18)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                        ) {
                            itemsIndexed(reviews) { _, item ->
                                Review(item, navController)
                            }
                        }
                    }

                    if (user!!.id != AppConfig.currentUser!!.id) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            Button(
                                onClick = { navController.navigate("add_review/${user!!.id}") },
                                colors = ButtonDefaults.buttonColors(containerColor = MyMint),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(20.dp, 0.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Оставить отзыв ", style = white18)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
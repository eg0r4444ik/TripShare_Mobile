package ru.vsu.tripshare_mobile.screens.profile_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.models.CarModel
import ru.vsu.tripshare_mobile.models.UserModel
import ru.vsu.tripshare_mobile.services.CarService
import ru.vsu.tripshare_mobile.services.UserService
import ru.vsu.tripshare_mobile.ui.theme.darkGray24
import ru.vsu.tripshare_mobile.ui.theme.darkGray48
import ru.vsu.tripshare_mobile.ui.theme.mint16
import ru.vsu.tripshare_mobile.ui.theme.mint18

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun UserProfile(userId: Int, person: UserModel, navController: NavController){

    var user by remember { mutableStateOf<UserModel?>(null) }

    LaunchedEffect(Unit) {
        val userResult = UserService.getUser(userId)
        if(userResult.isSuccess) {
            user = userResult.getOrNull()!!
        }
    }

    Scaffold(
        content = {
            if(user != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.White)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasicInformation(user!!, person, navController = navController)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(15.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if(user!!.rating != null) {
                                Image(
                                    painter = painterResource(id = R.drawable.star),
                                    contentDescription = "star",
                                    modifier = Modifier
                                        .size(60.dp)
                                )

                                Text(text = user!!.rating.toString(), style = darkGray48)
                            }else{
                                Image(
                                    painter = painterResource(id = R.drawable.star),
                                    contentDescription = "star",
                                    modifier = Modifier
                                        .size(60.dp)
                                )

                                Text(text = "Нет отзывов", style = darkGray24)
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(5.dp)
                                    .clickable {
                                        navController.navigate("reviews/${user!!.id}")
                                    },
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Посмотреть",
                                        style = if (user!!.rating != null) mint18 else mint16,
                                    )
                                    Text(
                                        text = "отзывы",
                                        style = if (user!!.rating != null) mint18 else mint16,
                                    )
                                }
                            }
                        }
                    }

                    Preferences(person, user!!, navController = navController)

                    val scope = rememberCoroutineScope()
                    var cars by remember { mutableStateOf(listOf<CarModel>()) }
                    var isLoading by remember { mutableStateOf(true) }

                    LaunchedEffect(user!!.id) {
                        scope.launch(Dispatchers.Main) {
                            val myCarsResult = CarService.getUsersCars(user!!.id)
                            myCarsResult.onSuccess { carList ->
                                cars = carList ?: listOf()
                                isLoading = false
                            }.onFailure {
                                // Handle failure
                                isLoading = false
                            }
                        }
                    }

                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Cars(cars, person, user!!, navController = navController)
                    }
                }
            }
        }
    )
}
package ru.vsu.tripshare_mobile.bottom_navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.montserrat

@Composable
fun BottomNavigation(navController: NavController){

    val listItems = listOf(
        BottomItem.FindTripScreen,
        BottomItem.AddTripScreen,
        BottomItem.TripsScreen,
        BottomItem.ChatsScreen,
        BottomItem.ProfileScreen
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier.height(76.dp),
        elevation = 15.dp
    ){

        Canvas(modifier = Modifier.height(1.dp)) {
            drawLine(
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                color = MyDarkGray,
                strokeWidth = 1f
            )
        }

        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEach{item ->
            BottomNavigationItem(
                modifier = Modifier,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                          },
                icon = {
                    Box(
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            painterResource(id = item.iconId),
                            contentDescription = "Icon",
                            tint = if (currentRoute == item.route) MyMint else MyDarkGray
                        )
                    }
                },
                label = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp,
                            fontFamily = montserrat,
                            color = if (currentRoute == item.route) MyMint else MyDarkGray,
                        )
                    }
                },
                selectedContentColor = MyMint,
                unselectedContentColor = MyDarkGray
            )
        }
    }
}
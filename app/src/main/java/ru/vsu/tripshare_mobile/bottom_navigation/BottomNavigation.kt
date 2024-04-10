package ru.vsu.tripshare_mobile.bottom_navigation

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.vsu.tripshare_mobile.ui.theme.MyDarkGray
import ru.vsu.tripshare_mobile.ui.theme.MyMint
import ru.vsu.tripshare_mobile.ui.theme.montserrat

@Composable
fun BottomNavigation(navController: NavController){

    val listItems = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2,
        BottomItem.Screen3,
        BottomItem.Screen4,
        BottomItem.Screen5
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = Color.White
    ){
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEach{item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                          },
                icon = { 
                    Icon(painterResource(id = item.iconId), contentDescription = "Icon")
                },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 7.sp,
                        fontFamily = montserrat
                    )
                },
                selectedContentColor = MyMint,
                unselectedContentColor = MyDarkGray
            )
        }
    }
}
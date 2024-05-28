package ru.vsu.tripshare_mobile.screens.profile_screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.tripshare_mobile.R
import ru.vsu.tripshare_mobile.models.ReviewModel
import ru.vsu.tripshare_mobile.ui.theme.darkGray14
import ru.vsu.tripshare_mobile.ui.theme.mint18

@Composable
fun Review(review: ReviewModel, navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { navController.navigate("user_profile" + "/${review.authorId.id}" ) }
                ) {
                    Image(
                        //todo заменить !! на проверку на null
                        painter = painterResource(id = review.authorId.avatarId!!),
                        contentDescription = "author",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )

                    Text(text = review.authorId.name, style = darkGray14)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var curr = 0
                    while (curr != 5) {
                        if (curr >= review.grade) {
                            Image(
                                painter = painterResource(id = R.drawable.gray_star),
                                contentDescription = "star",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = "star",
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }
                        curr++;
                    }
                }
            }

            Text(text = "Текст отзыва: ", style = mint18, modifier = Modifier.padding(10.dp, 0.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
                contentAlignment = Alignment.Center
            ){
                Text(text = review.comment, style = darkGray14)
            }
        }
    }
}
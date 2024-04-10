package ru.vsu.tripshare_mobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.vsu.tripshare_mobile.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val montserrat = FontFamily(
    Font(R.font.monserrat)
)

val black28 = TextStyle(
    color = Color.Black,
    fontWeight = FontWeight.Bold,
    fontSize = 28.sp,
    fontFamily = montserrat
)

val darkGray18 = TextStyle(
    color = MyDarkGray,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    fontFamily = montserrat
)

val darkGray14 = TextStyle(
    color = MyDarkGray,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    fontFamily = montserrat
)

val blue18 = TextStyle(
    color = MyBlue,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    fontFamily = montserrat
)

val white14 = TextStyle(
    color = Color.White,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    fontFamily = montserrat
)

val mint20 = TextStyle(
    color = MyMint,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    fontFamily = montserrat
)

val mint24 = TextStyle(
    color = MyMint,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    fontFamily = montserrat
)

val mint36 = TextStyle(
    color = MyMint,
    fontWeight = FontWeight.Bold,
    fontSize = 36.sp,
    fontFamily = montserrat
)
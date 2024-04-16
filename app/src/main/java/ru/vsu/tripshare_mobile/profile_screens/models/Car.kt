package ru.vsu.tripshare_mobile.profile_screens.models

data class Car(
    val brand: String,
    val model: String,
    val color: String,
    val manufactureYear: Int,
    val imageIds: List<Int>
)

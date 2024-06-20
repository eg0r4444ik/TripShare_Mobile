package ru.vsu.tripshare_mobile.models

data class CarModel(
    val id: Int,
    val brand: String,
    val model: String,
    val color: String,
    val manufactureYear: Int,
    var imageUrl: String?
)

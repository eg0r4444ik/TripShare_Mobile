package ru.vsu.tripshare_mobile.models

data class CarModel(
    val brand: String,
    val model: String,
    val color: String,
    val manufactureYear: Int,
    var imageIds: MutableList<Int>
)

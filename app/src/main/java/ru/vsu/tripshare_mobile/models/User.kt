package ru.vsu.tripshare_mobile.models

data class User(
    val name: String,
    val surname: String,
    val phone: String,
    val email: String?,
    val birthday: String,
    val rating: Double,
    val imageId: Int?,
    val cars: List<Car>?,
    val reviews: List<Review>,
    val musicPreferences: String?,
    val sociability: String?,
    val attitudeTowardsSmoking: String?,
    val attitudeTowardsAnimals: String?,
    val info: String?
)

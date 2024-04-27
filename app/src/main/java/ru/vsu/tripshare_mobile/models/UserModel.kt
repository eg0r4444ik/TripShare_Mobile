package ru.vsu.tripshare_mobile.models

data class UserModel(
    var userId: Int,
    var name: String,
    var surname: String,
    var phone: String,
    var email: String?,
    var birthday: String,
    var rating: Double,
    var imageId: Int?,
    var cars: MutableList<CarModel>?,
    var reviews: MutableList<ReviewModel>?,
    var musicPreferences: String?,
    var sociability: String?,
    var attitudeTowardsSmoking: String?,
    var attitudeTowardsAnimals: String?,
    var info: String?
)

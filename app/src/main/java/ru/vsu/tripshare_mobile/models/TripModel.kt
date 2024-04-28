package ru.vsu.tripshare_mobile.models

data class TripModel(
    val id: Int,
    val status: TripStatus,
    val cityFrom: String,
    val cityTo: String,
    val addressFrom: String,
    val addressTo: String,
    val daysUntil: String,
    val departureDate: String,
    val departureTime: String,
    val arrivalDate: String,
    val arrivalTime: String,
    val participants: List<UserModel>,
    val cost: Int
)

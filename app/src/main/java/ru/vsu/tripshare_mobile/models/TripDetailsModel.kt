package ru.vsu.tripshare_mobile.models

data class TripDetailsModel(
    val addressFrom: String,
    val addressTo: String,
    val departureDate: String,
    val departureTime: String,
    val arrivalDate: String,
    val arrivalTime: String,
    val travelTime: String,
    val participants: List<User>,
    val cost: Int,
    val car: Car
)

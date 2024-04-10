package ru.vsu.tripshare_mobile

data class MyTripAsPassengerModel(
    val cityFrom: String,
    val cityTo: String,
    val daysUntil: String,
    val departureDate: String,
    val departureTime: String,
    val arrivalDate: String,
    val arrivalTime: String,
    val driverName: String,
    val driverSurname: String,
    val imageId: Int,
    val cost: Int
)

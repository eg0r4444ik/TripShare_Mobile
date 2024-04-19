package ru.vsu.tripshare_mobile.models

data class MyTripModel(
    val status: TripStatus,
    val cityFrom: String,
    val cityTo: String,
    val daysUntil: String,
    val departureDate: String,
    val departureTime: String,
    val arrivalDate: String,
    val arrivalTime: String,
    val participants: List<TripParticipant>,
    val cost: Int
)

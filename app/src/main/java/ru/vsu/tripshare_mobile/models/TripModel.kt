package ru.vsu.tripshare_mobile.models

data class TripModel(
    val id: Int,
    val cityFrom: String,
    val cityTo: String,
    val stops: List<StopModel>,
    val daysUntil: String,
    val departureDate: String,
    val departureTime: String,
    val arrivalDate: String,
    val arrivalTime: String,
    val driver: UserModel,
    val participants: List<UserModel>,
    val maxTwoPassengersInTheBackSeat: Boolean,
    val smokingAllowed: Boolean,
    val petsAllowed: Boolean,
    val freeTrunk: Boolean,
    val carId: Int?,
    var status: TripStatus,
    val cost: Int,
    val participantsCount: Int
)

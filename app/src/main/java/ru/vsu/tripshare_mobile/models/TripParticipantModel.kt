package ru.vsu.tripshare_mobile.models

data class TripParticipantModel (
    val user: UserModel,
    val trip: TripModel,
    val distance: Double,
    val travelTime: String,
    val cost: Int,
    val addressFrom: String,
    val addressTo: String
)
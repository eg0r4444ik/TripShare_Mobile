package ru.vsu.tripshare_mobile.models

data class TripParticipantModel (
    val id: Int,
    val user: UserModel,
    val trip: TripModel,
    val status: TripStatus,
    val distance: Double,
    val travelTime: String,
    val cost: Int,
    val addressFrom: String,
    val addressTo: String
)
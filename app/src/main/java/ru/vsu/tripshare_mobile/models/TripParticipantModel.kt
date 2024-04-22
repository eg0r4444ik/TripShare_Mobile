package ru.vsu.tripshare_mobile.models

data class TripParticipantModel(
    val person: User,
    val addressFrom: String,
    val addressTo: String
)

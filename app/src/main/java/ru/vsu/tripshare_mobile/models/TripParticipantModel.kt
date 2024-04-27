package ru.vsu.tripshare_mobile.models

data class TripParticipantModel(
    val person: UserModel,
    val addressFrom: String,
    val addressTo: String
)

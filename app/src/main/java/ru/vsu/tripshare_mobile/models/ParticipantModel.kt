package ru.vsu.tripshare_mobile.models

data class ParticipantModel(
    val person: UserModel,
    val addressFrom: String,
    val addressTo: String
)

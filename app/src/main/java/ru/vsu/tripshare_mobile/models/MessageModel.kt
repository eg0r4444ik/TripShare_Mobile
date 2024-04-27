package ru.vsu.tripshare_mobile.models

import java.util.Date

data class MessageModel(
    val sender: UserModel,
    val text: String,
    val isRead: Boolean,
    val time: Date
)

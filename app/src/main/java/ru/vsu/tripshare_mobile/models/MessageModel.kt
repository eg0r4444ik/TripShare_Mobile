package ru.vsu.tripshare_mobile.models

import java.util.Date

data class MessageModel(
    val sender: User,
    val text: String,
    val isRead: Boolean,
    val time: Date
)

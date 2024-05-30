package ru.vsu.tripshare_mobile.models

import java.util.Date

data class MessageModel(
    val senderId: Int,
    val text: String,
    val isRead: Boolean,
    val time: Date
)

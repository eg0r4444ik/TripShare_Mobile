package ru.vsu.tripshare_mobile.models

data class ChatModel(
    val user: User,
    val companion: User,
    val messages: List<MessageModel>
)

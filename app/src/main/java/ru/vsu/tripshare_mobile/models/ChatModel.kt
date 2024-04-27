package ru.vsu.tripshare_mobile.models

data class ChatModel(
    val id: Int,
    val user: User,
    val companion: User,
    val messages: MutableList<MessageModel>
)

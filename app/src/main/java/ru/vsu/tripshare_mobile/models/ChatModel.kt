package ru.vsu.tripshare_mobile.models

data class ChatModel(
    val id: Int,
    val user: UserModel,
    val companion: UserModel,
    val messages: MutableList<MessageModel>
)

package ru.vsu.tripshare_mobile.models

data class ChatModel(
    val id: Int,
    var user: UserModel,
    val companion: UserModel,
    var messages: MutableList<MessageModel>
)

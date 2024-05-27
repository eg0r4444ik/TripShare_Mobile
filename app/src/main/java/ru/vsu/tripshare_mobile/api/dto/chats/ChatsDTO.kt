package ru.vsu.tripshare_mobile.api.dto.chats

class ChatsDTO {
    val user_id_1: Int
    val user_id_2: Int
    val id: Int

    constructor(user_id_1: Int, user_id_2: Int, id: Int) {
        this.user_id_1 = user_id_1
        this.user_id_2 = user_id_2
        this.id = id
    }
}
package ru.vsu.tripshare_mobile.api.dto.chats

class MessageDTO {
    val text: String?
    val image_id: String?
    val id: Int?
    val sender_id: Int?
    val chat_id: Int?

    constructor(text: String?, image_id: String?, id: Int, sender_id: Int, chat_id: Int) {
        this.text = text
        this.image_id = image_id
        this.id = id
        this.sender_id = sender_id
        this.chat_id = chat_id
    }

    constructor(text: String?, image_id: String?) {
        this.text = text
        this.image_id = image_id
        this.id = null
        this.sender_id = null
        this.chat_id = null
    }
}
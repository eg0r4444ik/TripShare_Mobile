package ru.vsu.tripshare_mobile.api.dto.chats

class MessageDTO {
    val text: String?
    val image_id: String?
    val id: Int?
    val created_at: String?
    val sender_id: Int?
    val receiver_id: Int?
    val chat_id: Int?

    constructor(text: String?, image_id: String?, id: Int, created_at: String?, sender_id: Int, chat_id: Int) {
        this.text = text
        this.image_id = image_id
        this.id = id
        this.created_at = created_at
        this.sender_id = sender_id
        this.receiver_id = null
        this.chat_id = chat_id
    }

    constructor(text: String?, image_id: String?, receiver_id: Int?) {
        this.text = text
        this.image_id = image_id
        this.receiver_id = receiver_id
        this.id = null
        this.created_at = null
        this.sender_id = null
        this.chat_id = null
    }
}
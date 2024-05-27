package ru.vsu.tripshare_mobile.api.dto.reviews

class ReviewDTO {
    val text: String
    val rating: Int
    val image_id: Int
    val user_id: Int
    val writer_id: Int?
    val id: Int?

    constructor(text: String, rating: Int, image_id: Int, user_id: Int, writer_id: Int, id: Int) {
        this.text = text
        this.rating = rating
        this.image_id = image_id
        this.user_id = user_id
        this.writer_id = writer_id
        this.id = id
    }

    constructor(text: String, rating: Int, image_id: Int, user_id: Int) {
        this.text = text
        this.rating = rating
        this.image_id = image_id
        this.user_id = user_id
        this.writer_id = null
        this.id = null
    }
}
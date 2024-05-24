package ru.vsu.tripshare_mobile.api.dto

class TokenDTO {
    val access_token: String
    val token_type: String

    constructor(
        access_token: String,
        token_type: String,
    ){
        this.access_token = access_token
        this.token_type = token_type
    }
}
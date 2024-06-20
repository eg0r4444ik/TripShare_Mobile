package ru.vsu.tripshare_mobile.api.dto.payments

class PaymentDTO {
    val amount: Int
    val status: PaymentStatusDTO
    val datetime_cr: String
    val datetime_fn: String
    val from_user_id: Int
    val to_user_id: Int
    val trip_id: Int
    val request_id: Int
    val id: Int

    constructor(
        amount: Int,
        status: PaymentStatusDTO,
        datetime_cr: String,
        datetime_fn: String,
        from_user_id: Int,
        to_user_id: Int,
        trip_id: Int,
        request_id: Int,
        id: Int
    ) {
        this.amount = amount
        this.status = status
        this.datetime_cr = datetime_cr
        this.datetime_fn = datetime_fn
        this.from_user_id = from_user_id
        this.to_user_id = to_user_id
        this.trip_id = trip_id
        this.request_id = request_id
        this.id = id
    }
}
package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.reviews.ReviewDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.ReviewModel
import ru.vsu.tripshare_mobile.models.UserModel

object ReviewService {

    suspend fun addReview(reviewModel: ReviewModel){
        return withContext(Dispatchers.IO) {
            try {
                val reviewDTO = fromModelToDTO(reviewModel)
                AppConfig.retrofitAPI.addReview(reviewDTO)
            } catch (e: Exception) {
            }
        }
    }

    suspend fun getReview(reviewId: Int): Result<ReviewModel>{
        return withContext(Dispatchers.IO) {
            try {
                val reviewDTO = AppConfig.retrofitAPI.getReview(reviewId)
                val writer = UserService.getUser(reviewDTO.writer_id!!)
                var review: ReviewModel? = null
                if(writer.isSuccess) {
                    review = fromDTOtoModel(reviewDTO, writer.getOrNull()!!)
                }
                Result.success(review!!)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    fun deleteReview(reviewId: Int){
        try {
            AppConfig.retrofitAPI.deleteReview(reviewId)
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    suspend fun getMyReviews(): Result<List<ReviewModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val reviewsDTO = AppConfig.retrofitAPI.getMyReviews()
                val reviews = mutableListOf<ReviewModel>()
                reviewsDTO.forEach{
                    val writer = UserService.getUser(it.writer_id!!)
                    if(writer.isSuccess) {
                        reviews.add(fromDTOtoModel(it, writer.getOrNull()!!))
                    }
                }
                Result.success(reviews)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getUsersReviews(userId: Int): Result<List<ReviewModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val reviewsDTO = AppConfig.retrofitAPI.getUsersReviews(userId)
                val reviews = mutableListOf<ReviewModel>()
                reviewsDTO.forEach{
                    val writer = UserService.getUser(it.writer_id!!)
                    if(writer.isSuccess) {
                        reviews.add(fromDTOtoModel(it, writer.getOrNull()!!))
                    }
                }
                Result.success(reviews)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun fromModelToDTO(reviewModel: ReviewModel): ReviewDTO {
        val reviewDTO = ReviewDTO(
            reviewModel.comment,
            reviewModel.grade,
            null,
            reviewModel.author.id
        )
        return reviewDTO
    }

    private fun fromDTOtoModel(reviewDTO: ReviewDTO, writer: UserModel): ReviewModel {
        val reviewModel = ReviewModel(
            writer,
            reviewDTO.rating,
            reviewDTO.text
        )
        return reviewModel
    }

}
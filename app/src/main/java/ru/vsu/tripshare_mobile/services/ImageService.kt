package ru.vsu.tripshare_mobile.services

import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.utils.ImageUtils

object ImageService {

    fun addImage(bitmap: Bitmap): Int{
        try {
            val base64String = ImageUtils.bitmapToBase64(bitmap);
            return AppConfig.retrofitAPI.addImage(base64String)
        } catch (e: Exception) {
            e.stackTrace
            return -1;
        }
    }

    suspend fun getImage(imageId: Int): Result<Bitmap>{
        return withContext(Dispatchers.IO) {
            try {
                val bitmap = ImageUtils.base64ToBitmap(AppConfig.retrofitAPI.getImage(imageId), )
                Result.success(bitmap)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}
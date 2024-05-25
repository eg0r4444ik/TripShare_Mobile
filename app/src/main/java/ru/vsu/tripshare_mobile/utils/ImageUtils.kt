package ru.vsu.tripshare_mobile.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object ImageUtils {

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun resourceToBase64(resourceId: Int, context: Context): String? {
        val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)
        return bitmapToBase64(bitmap)
    }

    fun base64ToImage(base64String: String, outputFilePath: String): Boolean {
        try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            val outputStream = FileOutputStream(File(outputFilePath))
            outputStream.write(decodedBytes)
            outputStream.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

}
package ru.vsu.tripshare_mobile.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.services.UserService
import java.io.ByteArrayOutputStream

object ImageUtils {

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun base64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun saveUserImage(bitmap: Bitmap){
        val storage = Firebase.storage.reference.child("images")
        val task = storage.child(generateRandomString()).putBytes(
            bitmapToByteArray(bitmap)
        )
        task.addOnSuccessListener { uploadTask ->
            uploadTask.metadata?.reference
                ?.downloadUrl?.addOnCompleteListener{ uriTask ->
                    val uri = uriTask.result.toString()
                    AppConfig.currentUser!!.avatarUrl = uri
                    CoroutineScope(Dispatchers.Main).launch {
                        UserService.updateMe()
                    }
                }
        }
    }

    fun saveCarImage(bitmap: Bitmap){
        var uri: String? = null
        val storage = Firebase.storage.reference.child("images")
        val task = storage.child(generateRandomString()).putBytes(
            bitmapToByteArray(bitmap)
        )
        task.addOnSuccessListener { uploadTask ->
            uploadTask.metadata?.reference
                ?.downloadUrl?.addOnCompleteListener{ uriTask ->
                    uri = uriTask.result.toString()
                    AppConfig.currentCarImageUrl = uri
                }
        }
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray{
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    fun generateRandomString(length: Int = 15): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { characters.random() }
            .joinToString("")
    }

}
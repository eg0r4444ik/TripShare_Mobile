package ru.vsu.tripshare_mobile.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
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

    fun saveImage(bitmap: Bitmap){
        val storage = Firebase.storage.reference.child("images")
        val task = storage.child("images").putBytes(
            bitmapToByteArray(bitmap)
        )
        task.addOnSuccessListener { uploadTask ->
            uploadTask.metadata?.reference
                ?.downloadUrl?.addOnCompleteListener{ uriTask ->
                    val uri = uriTask.result.toString()
                    print(uri)
                }
        }
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray{
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

}
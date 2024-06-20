package ru.vsu.tripshare_mobile.services

import android.widget.Toast
import ru.vsu.tripshare_mobile.config.AppConfig

object ValidationService {

    fun <T> validate(item: Result<T>?, exceptionText: String): T? {
        if(item != null && item.isSuccess && item.getOrNull() != null){
            return item.getOrNull()!!
        }else{
            Toast.makeText(
                AppConfig.appContext,
                exceptionText,
                Toast.LENGTH_LONG
            ).show()
        }

        return null
    }

}
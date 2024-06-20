package ru.vsu.tripshare_mobile.data_store

import android.content.Context
import android.content.SharedPreferences

class AuthTokenDataStore(private val context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clearToken() {
        val editor = prefs.edit()
        editor.remove("auth_token")
        editor.apply()
    }
}
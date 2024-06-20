package ru.vsu.tripshare_mobile.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.vsu.tripshare_mobile.data_store.AuthTokenDataStore

class AuthInterceptor(private val authManager: AuthTokenDataStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${authManager.getToken()}")
            .build()
        return chain.proceed(newRequest)
    }
}
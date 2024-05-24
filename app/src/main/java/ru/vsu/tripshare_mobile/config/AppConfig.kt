package ru.vsu.tripshare_mobile.config

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.tripshare_mobile.api.AuthInterceptor
import ru.vsu.tripshare_mobile.api.RetrofitApi
import ru.vsu.tripshare_mobile.data_store.AuthTokenDataStore
import ru.vsu.tripshare_mobile.models.UserModel

object AppConfig {

    lateinit var appContext: Context

    val BASE_URL = "http://45.91.8.198:8000/"

    val gson = GsonBuilder()
        .setLenient()
        .create()

    lateinit var authManager: AuthTokenDataStore

    lateinit var okHttpClient: OkHttpClient

    lateinit var retrofit: Retrofit

    lateinit var retrofitAPI: RetrofitApi

    var currentUser: UserModel? = null

    fun init(context: Context) {
        appContext = context
        authManager = AuthTokenDataStore(appContext)
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authManager))
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofitAPI = retrofit.create(RetrofitApi::class.java)
    }

    fun initUser(user: UserModel?){
        currentUser = user
    }

}
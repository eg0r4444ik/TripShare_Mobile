package ru.vsu.tripshare_mobile.config

import android.content.Context
import com.google.gson.GsonBuilder
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.tripshare_mobile.api.AuthInterceptor
import ru.vsu.tripshare_mobile.api.RetrofitApi
import ru.vsu.tripshare_mobile.data_store.AuthTokenDataStore
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.UserModel

object AppConfig {

    lateinit var appContext: Context

    val config = AppMetricaConfig.newConfigBuilder("4ce5c2a5-d516-4f95-821f-e5dfaceddc76").build()

    val BASE_URL = "http://193.222.62.211:8000/"

    val gson = GsonBuilder()
        .setLenient()
        .create()

    lateinit var authManager: AuthTokenDataStore

    lateinit var okHttpClient: OkHttpClient

    lateinit var retrofit: Retrofit

    lateinit var retrofitAPI: RetrofitApi

    var currentUser: UserModel? = null

    var currentChats: List<ChatModel>? = null

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
        AppMetrica.activate(appContext, config)
    }

}
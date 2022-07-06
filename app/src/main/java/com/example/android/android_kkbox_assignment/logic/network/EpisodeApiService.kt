package com.example.android.android_kkbox_assignment.logic.network

import com.example.android.android_kkbox_assignment.logic.model.Rss
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://feeds.soundcloud.com/users/soundcloud:users:322164009/"

private val client = OkHttpClient.Builder()
    .addInterceptor(
        HttpLoggingInterceptor().apply {
//            level =  HttpLoggingInterceptor.Level.BODY
        }
    )
    .build()

private val tikXml = TikXml.Builder()
    .exceptionOnUnreadXml(false)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(TikXmlConverterFactory.create(tikXml))
    .client(client)
    .baseUrl(BASE_URL)
    .build()

interface EpisodeApiService {
    
    @GET("sounds.rss")
    suspend fun getRSSData() : Rss
    
}

object EpisodeApi {
    val retrofitService: EpisodeApiService by lazy { retrofit.create(EpisodeApiService::class.java) }
}
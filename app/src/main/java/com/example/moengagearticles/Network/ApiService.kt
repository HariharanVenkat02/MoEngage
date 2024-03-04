package com.example.moengagearticles.Network

import com.example.moengagearticles.Data.Articles
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("Android/news-api-feed/staticResponse.json")
    suspend fun getArticlesList(): Response<Articles>

    companion object {
        operator fun invoke(): ApiService {
            val BASE_IP = "https://candidate-test-data-moengage.s3.amazonaws.com/"
            return Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .baseUrl(BASE_IP)
                .client(getOkHTTPClient())
                .build()
                .create(ApiService::class.java)
        }

        private val DATE_FORMATS = arrayOf(
            "dd/MM/yyyy HH:mm:ss",
            "dd/MM/yyyy"
        )

        private fun getGson(): Gson = GsonBuilder()
            .setDateFormat(DATE_FORMATS[0])
            .setDateFormat(DATE_FORMATS[1])
            .setLenient()
            .create()

        fun getOkHTTPClient(): OkHttpClient {
            return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
        }
    }
}
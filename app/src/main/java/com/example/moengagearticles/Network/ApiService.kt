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

    // Define the endpoint for getting articles
    @GET("Android/news-api-feed/staticResponse.json")
    suspend fun getArticlesList(): Response<Articles>

    companion object {
        // Base URL for the API
        private const val BASE_URL = "https://candidate-test-data-moengage.s3.amazonaws.com/"

        // Create and configure Retrofit instance
        operator fun invoke(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHTTPClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build()
                .create(ApiService::class.java)
        }

        // Define date formats for Gson
        private val DATE_FORMATS = arrayOf(
            "dd/MM/yyyy HH:mm:ss",
            "dd/MM/yyyy"
        )

        // Create and configure Gson instance
        private fun getGson(): Gson = GsonBuilder()
            .setDateFormat(DATE_FORMATS[0])
            .setDateFormat(DATE_FORMATS[1])
            .setLenient()
            .create()

        // Create and configure OkHttpClient with logging interceptor
        private fun getOkHTTPClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }
    }
}
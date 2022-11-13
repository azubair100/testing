package com.zubair.test.network

import com.zubair.test.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkBuilder {
    private const val NETWORK_CONNECTION_TIMEOUT = 300L
    private const val NETWORK_IO_TIMEOUT = 30L

    fun create() : BookApi {
        return  Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApi::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(NETWORK_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(NETWORK_IO_TIMEOUT, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(NETWORK_IO_TIMEOUT, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(interceptor)
        return okHttpClientBuilder.build()
    }
}
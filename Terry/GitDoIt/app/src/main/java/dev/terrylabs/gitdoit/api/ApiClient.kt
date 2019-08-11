package dev.terrylabs.gitdoit.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val URL = "https://api.github.com"
    private var mRetrofit: Retrofit? = null

    val instance: Retrofit
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build()

            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return mRetrofit!!
        }
}
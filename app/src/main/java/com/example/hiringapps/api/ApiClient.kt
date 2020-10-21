package com.example.hiringapps.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/"
        private var retrofit: Retrofit? = null

        private fun provideHTTPLoggingInterceptor() = run {
            HttpLoggingInterceptor().apply {
                apply { level = HttpLoggingInterceptor.Level.BODY }
            }
        }

        fun getApiClient(mContext: Context) : Retrofit? {
            if (retrofit == null) {
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(provideHTTPLoggingInterceptor())
                    .addInterceptor(
                        HeaderInterceptor(
                            mContext
                        )
                    )
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}
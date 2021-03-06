package com.example.hiringapps.api

import android.content.Context
import com.example.hiringapps.sharedpref.Constant
import com.example.hiringapps.sharedpref.SharedPrefProvider
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor (private val mContext: Context) : Interceptor {

    private lateinit var sharedPref: SharedPrefProvider

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        sharedPref = SharedPrefProvider(mContext)
        val token = sharedPref.getString(Constant.KEY_TOKEN) ?: ""

        proceed(
            request().newBuilder()
                .addHeader("Authorization", "Bareer $token")
                .build()
        )
    }
}
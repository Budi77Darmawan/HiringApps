package com.example.hiringapps.api.profile

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApiService {

    @GET("recruiters/{id}")
    suspend fun getProfilebyID(@Path("id") id: String): ProfileResponse
}
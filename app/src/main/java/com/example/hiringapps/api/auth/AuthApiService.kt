package com.example.hiringapps.api.auth

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {

    @FormUrlEncoded
    @POST("account/login")
    suspend fun loginRequest(@Field("email") email: String?,
                             @Field("password") password: String?) : LoginResponse
}
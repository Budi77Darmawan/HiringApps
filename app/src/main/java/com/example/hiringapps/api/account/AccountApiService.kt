package com.example.hiringapps.api.account

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface AccountApiService {

    @FormUrlEncoded
    @POST("account/register")
    suspend fun registerRequest(@Field("roleAccount") role: String?,
                                @Field("name") name: String?,
                                @Field("email") email: String?,
                                @Field("companyName") companyName: String?,
                                @Field("position") position: String?,
                                @Field("numberPhone") numberPhone: String?,
                                @Field("password") password: String?) : RegisterResponse

    @FormUrlEncoded
    @PATCH("account")
    suspend fun updateAccountRequest(
        @Field("name") name: String?)

    @Multipart
    @PATCH("recruiters")
    suspend fun updateRecruitersRequest(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part): UpdateRecruitersResponse

    @Multipart
    @PATCH("recruiters")
    suspend fun updateRecruiters2Request(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>): UpdateRecruitersResponse

}
package com.example.hiringapps.api.hireproject

import com.example.hiringapps.api.project.ProjectResponse
import retrofit2.http.*

interface HireApiService {

    @FormUrlEncoded
    @POST("hireproject")
    suspend fun posthireproject(
        @Field("idProject") id_project: String?,
        @Field("idFree") id_freelancer: String?,
        @Field("message") message: String?,
        @Field("projectJob") projectJob: String?,
        @Field("price") price: String?
    ): HireResponse

    @GET("hireproject")
    suspend fun getlisthireproject(): HireListResponse
}
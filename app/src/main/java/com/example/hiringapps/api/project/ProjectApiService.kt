package com.example.hiringapps.api.project

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ProjectApiService {

    @GET("project/{id}")
    suspend fun getProjectbyIDRequest(
        @Path("id") id: String
    ): ProjectResponse

    @Multipart
    @POST("project")
    suspend fun postProject(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part
    ): AddProjectResponse

    @Multipart
    @PATCH("project/{id}")
    suspend fun updateProject(
        @Path("id") id: String?,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part
    ): UpdateProjectResponse

    @Multipart
    @PATCH("project/{id}")
    suspend fun updateProject2(
        @Path("id") id: String?,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>
    ): UpdateProjectResponse

    @DELETE("project/{id}")
    suspend fun deleteProject(@Path("id") id: Int?): DeleteProjectResponse

}
package com.example.hiringapps.api.experience

import com.example.hiringapps.api.project.ProjectResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ExperienceApiService {

    @GET("experience/{id}")
    suspend fun getExperiencebyIDRequest(
        @Path("id") id: String?
    ): ExperienceResponse
}
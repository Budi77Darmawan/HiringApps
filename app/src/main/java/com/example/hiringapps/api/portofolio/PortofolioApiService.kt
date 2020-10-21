package com.example.hiringapps.api.portofolio

import retrofit2.http.GET
import retrofit2.http.Path

interface PortofolioApiService {

    @GET("portofolio/{id}")
    suspend fun getPortofoliobyIDRequest(
        @Path("id") id: String?
    ): PortofolioResponse
}
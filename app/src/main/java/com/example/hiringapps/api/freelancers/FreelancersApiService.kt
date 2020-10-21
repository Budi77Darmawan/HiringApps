package com.example.hiringapps.api.freelancers

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FreelancersApiService {

    @GET("freelancers/{id}")
    suspend fun getFreelancersbyIDRequest(
        @Path("id") idFreelancer: String?
    ): FreelancersResponse

    @GET("freelancers/")
    fun getFreelancersRequest(
        @Query("search") search: String?,
        @Query("jobDesc") job: String?
    ): Call<FreelancersResponse>
}
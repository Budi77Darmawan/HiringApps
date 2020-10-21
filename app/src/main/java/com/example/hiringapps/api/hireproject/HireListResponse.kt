package com.example.hiringapps.api.hireproject

import com.google.gson.annotations.SerializedName

class HireListResponse (val success: Boolean, val message: String?, val data: List<HireProject>?) {

    data class HireProject (
        @SerializedName("id_project") val id_project: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("image") val image: String?,
        @SerializedName("project_name") val project_name: String?,
        @SerializedName("projectJob") val job: String?,
        @SerializedName("message") val message: String?,
        @SerializedName("price") val price: String?,
        @SerializedName("statusConfirm") val status: String?
    )
}
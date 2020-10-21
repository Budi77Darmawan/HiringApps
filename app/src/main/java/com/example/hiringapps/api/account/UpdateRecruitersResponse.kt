package com.example.hiringapps.api.account

import com.google.gson.annotations.SerializedName

class UpdateRecruitersResponse(val success: Boolean, val message: String?, val data: Recruiters?) {

    data class Recruiters (
        @SerializedName("companyName") val company: String?,
        @SerializedName("position") val position: String?,
        @SerializedName("sector") val sector: String?,
        @SerializedName("city") val city : String?,
        @SerializedName("website") val website: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("image") val image: String?
    )
}
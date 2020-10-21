package com.example.hiringapps.api.profile

import com.google.gson.annotations.SerializedName

class ProfileResponse(val success: Boolean, val message: String?, val data: List<Profile>?) {

    data class Profile (
        @SerializedName("name") val name: String?,
        @SerializedName("companyName") val company: String?,
        @SerializedName("position") val position: String?,
        @SerializedName("sector") val sector: String?,
        @SerializedName("city") val city: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("website") val website: String?,
        @SerializedName("image") val image: String?
    )
}
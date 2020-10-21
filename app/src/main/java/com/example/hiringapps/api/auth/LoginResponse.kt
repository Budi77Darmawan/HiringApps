package com.example.hiringapps.api.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(val success: Boolean, val message: String?, val data: DataResult?) {
    data class DataResult (
        @SerializedName("idAccount") val id: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("roleAccount") val role: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("status") val status: String?,
        @SerializedName("token") val token: String?
    )
}
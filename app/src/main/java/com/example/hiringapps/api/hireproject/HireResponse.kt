package com.example.hiringapps.api.hireproject

import com.google.gson.annotations.SerializedName

class HireResponse(val success: Boolean, val message: String?, val data: HireProject?) {

    data class HireProject (
        @SerializedName("id_accountRec") val id_rec: String?,
        @SerializedName("id_project") val id_project: String?,
        @SerializedName("id_accountFree") val id_free: String?,
        @SerializedName("message") val message: String?,
        @SerializedName("projectJob") val job: String?,
        @SerializedName("price") val price: String?
    )
}
package com.example.hiringapps.api.portofolio

import com.google.gson.annotations.SerializedName

class PortofolioResponse (val success: Boolean, val message: String?, val data: List<Portofolio>?) {

    data class Portofolio (
        @SerializedName("id_portofolio") val id_portofolio: String?,
        @SerializedName("id_account") val id_account: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("image") val image: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("linkRepo") val link_repo: String?,
        @SerializedName("typePorto") val type_portofolio: String?
    )
}
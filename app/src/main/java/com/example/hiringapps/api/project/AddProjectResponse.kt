package com.example.hiringapps.api.project

import com.google.gson.annotations.SerializedName

class AddProjectResponse (val success: Boolean, val message: String?, val data: Project?) {

    data class Project (
        @SerializedName("id_project") val id_project: String?,
        @SerializedName("name_project") val name_project: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("image") val image: String?
    )
}
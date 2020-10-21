package com.example.hiringapps.project

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProjectModel (
    var id: String = "",
    var name: String = "",
    var deadline: String = "",
    var description: String = "",
    var image: String = ""
) : Parcelable
package com.example.hiringapps.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExperienceModel (
    var id: String = "",
    var company: String = "",
    var job: String = "",
    var start: String = "",
    var end: String = "",
    var description: String = ""
) : Parcelable
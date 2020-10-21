package com.example.hiringapps.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailFreelancersModel(
    var id_freelancers: String = "",
    var name: String?,
    var job: String?,
    var status: String?,
    var city: String?,
    var description: String?,
    var skill: String?,
    var image: String?
): Parcelable
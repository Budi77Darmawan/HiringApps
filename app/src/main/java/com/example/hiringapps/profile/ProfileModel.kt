package com.example.hiringapps.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileModel (
    val name: String?,
    val company: String?,
    val position: String?,
    val sector: String?,
    val city: String?,
    val description: String?,
    val website: String?,
    val image: String?
) : Parcelable
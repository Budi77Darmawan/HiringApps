package com.example.hiringapps.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PortofolioModel(
    var id_portofolio: String = "",
    var id_freelancer: String?,
    var name: String?,
    var image: String?,
    var description: String?,
    var link_repo: String?,
    var type_portofolio: String?
): Parcelable
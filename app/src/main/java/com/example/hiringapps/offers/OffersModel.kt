package com.example.hiringapps.offers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OffersModel (
    var id_project: String = "",
    var name: String = "",
    var project_name: String = "",
    var projectJob: String = "",
    var message: String = "",
    var price: String = "",
    var status_confirm: String = "",
    var image: String = ""
) : Parcelable
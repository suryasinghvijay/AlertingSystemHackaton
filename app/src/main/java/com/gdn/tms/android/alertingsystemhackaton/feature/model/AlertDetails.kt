package com.gdn.tms.android.alertingsystemhackaton.feature.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlertDetails(
    @SerializedName("details")
    val details: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("generatedDate")
    val generatedDate: String,
    @SerializedName("sentBy")
    val sentBy: String,
    @SerializedName("sentTo")
    val sentTo: String,
    @SerializedName("severity")
    val severity: String,
    @SerializedName("squad")
    val squad: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("updatedBy")
    var updatedBy: String ?=null,
    @SerializedName("updatedDate")
    var updatedDate: String ?=null,
):Parcelable
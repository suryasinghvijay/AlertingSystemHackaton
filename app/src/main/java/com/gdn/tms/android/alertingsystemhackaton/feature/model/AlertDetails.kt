package com.gdn.tms.android.alertingsystemhackaton.feature.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlertDetails(
    @SerializedName("details")
    val details: String,
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
    val updatedBy: String,
    @SerializedName("updatedDate")
    val updatedDate: String
):Parcelable
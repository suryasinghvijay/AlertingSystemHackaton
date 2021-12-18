package com.gdn.tms.android.alertingsystemhackaton.feature.model

import com.google.gson.annotations.SerializedName

data class NotificationModel(
  @SerializedName("id") val id: String,
  @SerializedName("createdDate")
  val createdDate: String,
  @SerializedName("details") val details: String,
  @SerializedName("lastSyncAt") val lastSyncAt: String,
  @SerializedName("notificationMedium") val notificationMedium: String,
  @SerializedName("summary") val summary: String,
  @SerializedName("picName") val picName: String,
  @SerializedName("alert") val alert: AlertDetails
)
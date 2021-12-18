package com.gdn.tms.android.alertingsystemhackaton.feature.model

import com.google.gson.annotations.SerializedName

data class NotificationRequest(
  @SerializedName("")
  var list : MutableList<NotificationModel>
)
package com.gdn.tms.android.alertingsystemhackaton.feature.model

import com.google.gson.annotations.SerializedName

data class MembersItem(
  @SerializedName("email") var email: String? = null,
  @SerializedName("id") var id: String? = null,
  @SerializedName("name") var name: String? = null,
  @SerializedName("phone") var phone: String? = null,
  var squadName: String? = null,
  var notificationId: String? = null
)
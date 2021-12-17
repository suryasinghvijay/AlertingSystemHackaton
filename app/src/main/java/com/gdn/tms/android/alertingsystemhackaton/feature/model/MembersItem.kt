package com.gdn.tms.android.alertingsystemhackaton.feature.model

import com.google.gson.annotations.SerializedName

data class MembersItem(
    @SerializedName("email") val email: String,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String
)
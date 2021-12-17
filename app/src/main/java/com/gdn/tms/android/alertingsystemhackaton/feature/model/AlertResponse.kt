package com.gdn.tms.android.alertingsystemhackaton.feature.model

import com.google.gson.annotations.SerializedName

data class AlertResponse(
    @SerializedName("contents")
    val contents: List<AlertDetails>,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)
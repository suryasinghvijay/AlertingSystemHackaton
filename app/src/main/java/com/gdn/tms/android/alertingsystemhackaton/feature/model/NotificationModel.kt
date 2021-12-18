package com.gdn.tms.android.alertingsystemhackaton.feature.model

data class NotificationModel(
    val createdDate: String,
    val details: String,
    val lastSyncAt: String,
    val notificationMedium: String,
    val sentTo: String,
    val summary: String
)
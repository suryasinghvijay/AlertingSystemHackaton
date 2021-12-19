package com.gdn.tms.android.alertingsystemhackaton.feature.model

data class SquadDetailsResponse(
    val alertConfigurations: List<AlertConfiguration>,
    val calendars: List<Calendar>,
    val description: String,
    val id: String,
    val members: List<Member>,
    val name: String,
    val pageId: String,
    val services: List<Any>
)
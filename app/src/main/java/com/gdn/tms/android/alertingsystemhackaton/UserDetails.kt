package com.gdn.tms.android.alertingsystemhackaton

import com.gdn.tms.android.alertingsystemhackaton.feature.model.MembersItem
import com.gdn.tms.android.alertingsystemhackaton.feature.model.SquadDetailsResponse

object UserDetails {

  private val userDetails = MembersItem()

  fun updateUserDetails(membersItem: MembersItem) {
    membersItem.apply {
      userDetails.email = email
      userDetails.id = id
      userDetails.name = name
      userDetails.phone = phone
    }
  }

  fun updateSquadDetails(squadDetailsResponse: MutableList<SquadDetailsResponse>) {
    userDetails.squadName = squadDetailsResponse.first().name
    userDetails.notificationId = squadDetailsResponse.first().id
  }

  fun getUserName() = userDetails.name
  fun getSquadName() = userDetails.squadName

}
package com.gdn.tms.android.alertingsystemhackaton.feature.repository

import com.gdn.tms.android.alertingsystemhackaton.network.ApiService
import javax.inject.Inject

class MembersRepository @Inject constructor(private val apiService: ApiService) {
  suspend fun getMembers() = apiService.getMembers()
}
package com.gdn.tms.android.alertingsystemhackaton.feature.repository

import com.gdn.tms.android.alertingsystemhackaton.extension.safeApiCall
import com.gdn.tms.android.alertingsystemhackaton.network.ApiService
import javax.inject.Inject

class MembersRepository @Inject constructor(private val apiService: ApiService) : BaseRepository() {
  suspend fun getMembers() = apiService.getMembers()

  suspend fun fetchActiveAlertFromServer(squad: String,
    page: Int,
    size: Int) =
    safeApiCall(networkCoroutineDispatcher){
      apiService.fetchAlerts(squad, page, size)
  }
}
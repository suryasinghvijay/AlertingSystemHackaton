package com.gdn.tms.android.alertingsystemhackaton.feature.repository

import com.gdn.tms.android.alertingsystemhackaton.extension.safeApiCall
import com.gdn.tms.android.alertingsystemhackaton.network.ApiService
import javax.inject.Inject

class MembersRepository @Inject constructor(private val apiService: ApiService) : BaseRepository() {
  suspend fun getMembers() = apiService.getMembers()

  suspend fun getSquadDetails(username:String) = apiService.getSquadDetails(username)

  suspend fun fetchActiveAlertFromServer(
    squad: String, page: Int, size: Int, state: String
  ) =
    safeApiCall(networkCoroutineDispatcher){
      apiService.fetchAlerts(squad, page, size, state)
  }

  suspend fun fetchNotification(user: String, medium: String) =
    safeApiCall(networkCoroutineDispatcher){
      apiService.fetchNotification(user, medium)
    }

  suspend fun acceptNotification(id: String, status: String) = safeApiCall(networkCoroutineDispatcher){
    apiService.acceptNotification(id, status)
  }
  suspend fun acceptAlert(id: String, status: String) = safeApiCall(networkCoroutineDispatcher){
    apiService.acceptAlert(id, status)
  }
}
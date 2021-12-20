package com.gdn.tms.android.alertingsystemhackaton.network

import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertResponse
import com.gdn.tms.android.alertingsystemhackaton.feature.model.MembersItem
import com.gdn.tms.android.alertingsystemhackaton.feature.model.NotificationModel
import com.gdn.tms.android.alertingsystemhackaton.feature.model.SquadDetailsResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
  @GET("api/member/listAllMembers") suspend fun getMembers(): MutableList<MembersItem>

  @GET("api/squad/get-all-by-member") suspend fun getSquadDetails(
    @Query("member") userName: String
  ): MutableList<SquadDetailsResponse>

  @GET("api/alert/list") suspend fun fetchAlerts(
    @Query("squad") squad: String,
    @Query("page") page: Int,
    @Query("size") size: Int,
    @Query("status") state: String
  ): AlertResponse

  @GET("api/notification/fetch") suspend fun fetchNotification(
    @Query("user") user: String,
    @Query("medium") medium: String
  ): MutableList<NotificationModel>

  @POST("api/notification/acknowledge") suspend fun acceptNotification(
    @Query("notificationId") notificationId: String,
    @Query("status") status: String
  ): MutableList<NotificationModel>

  @POST("api/alert/acknowledge") suspend fun acceptAlert(
    @Query("alertId") alertId: String,
    @Query("status") status: String
  ): Boolean

}
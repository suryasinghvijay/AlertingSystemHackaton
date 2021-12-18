package com.gdn.tms.android.alertingsystemhackaton.network

import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertResponse
import com.gdn.tms.android.alertingsystemhackaton.feature.model.Members
import com.gdn.tms.android.alertingsystemhackaton.feature.model.NotificationModel
import com.gdn.tms.android.alertingsystemhackaton.feature.model.NotificationRequest
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
  @GET("api/member/list") suspend fun getMembers(): Members

  @GET("api/alert/list") suspend fun fetchAlerts(
    @Query("squad") squad: String, @Query("page") page: Int, @Query("size") size: Int
  ): AlertResponse

  @GET("api/notification/fetch") suspend fun fetchNotification( @Query("user") user: String,  @Query("medium") medium: String) : MutableList<NotificationModel>
}
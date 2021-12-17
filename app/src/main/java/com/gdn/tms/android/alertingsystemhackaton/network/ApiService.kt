package com.gdn.tms.android.alertingsystemhackaton.network

import com.gdn.tms.android.alertingsystemhackaton.feature.model.Members
import retrofit2.http.GET

interface ApiService {
  @GET("/api/member/list")
  suspend fun getMembers() : Members
}
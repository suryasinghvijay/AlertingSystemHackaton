package com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdn.tms.android.alertingsystemhackaton.SingleLiveEvent
import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertResponse
import com.gdn.tms.android.alertingsystemhackaton.feature.model.NotificationModel
import com.gdn.tms.android.alertingsystemhackaton.feature.repository.MembersRepository
import com.gdn.tms.android.alertingsystemhackaton.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardActivityViewModel @Inject constructor(private val repository: MembersRepository) : ViewModel(){
  val activeAlertLiveData = MutableLiveData<AlertResponse>()
  val notificationLiveData = MutableLiveData<MutableList<NotificationModel>>()
  val acceptAlertDetailsResponse = MutableLiveData<Boolean>()
  val pastAlertLiveData = MutableLiveData<AlertResponse>()
  val exceptionHandler = SingleLiveEvent<String>()


  fun fetchActiveAlertFromServer(
    squad: String, page: Int, size: Int, state: String
  ){
    viewModelScope.launch {
      when(val res = repository.fetchActiveAlertFromServer(squad, page, size, state)){
        is ResultWrapper.Success -> {
          if (state =="OPEN"){
            activeAlertLiveData.postValue(res.value!!)
          } else {
            pastAlertLiveData.postValue(res.value!!)
          }
        }
        is ResultWrapper.HttpError -> {
          Log.e("exception", "httpError")
        }
        is ResultWrapper.NetworkError -> {
          Log.e("exception", "NetworkError")
        }
      }
    }

  }

  fun fetchNotification(user: String, medium: String){
    viewModelScope.launch {
      when(val res = repository.fetchNotification(user, medium)){
        is ResultWrapper.Success -> {
          notificationLiveData.postValue(res.value!!)
        }
        is ResultWrapper.HttpError -> {
          Log.e("exception", "httpError")
        }
        is ResultWrapper.NetworkError -> {
          Log.e("exception", "NetworkError")
        }
      }
    }

  }

  fun acceptNotification(id: String, status: String) {
    viewModelScope.launch {
      when(val res = repository.acceptNotification(id, status)){
        is ResultWrapper.Success -> {
          notificationLiveData.postValue(res.value!!)
        }
        is ResultWrapper.HttpError -> {
          Log.e("exception", "httpError")
        }
        is ResultWrapper.NetworkError -> {
          Log.e("exception", "NetworkError")
        }
      }
    }
  }

  fun acceptAlert(id: String, status: String) {
    viewModelScope.launch {
      when(val res = repository.acceptAlert(id, status)){
        is ResultWrapper.Success -> {
          acceptAlertDetailsResponse.postValue(res.value!!)
        }
        is ResultWrapper.HttpError -> {
          Log.e("exception", "httpError")
        }
        is ResultWrapper.NetworkError -> {
          Log.e("exception", "NetworkError")
        }
      }
    }
  }
}
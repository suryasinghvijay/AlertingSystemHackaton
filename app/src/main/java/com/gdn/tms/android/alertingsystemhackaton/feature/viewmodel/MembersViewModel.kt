package com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdn.tms.android.alertingsystemhackaton.feature.model.Members
import com.gdn.tms.android.alertingsystemhackaton.feature.repository.MembersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MembersViewModel @Inject constructor(private val repository: MembersRepository) : ViewModel(){
  val membersLiveData : MutableLiveData<Members> = MutableLiveData()

  fun getMembers() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        membersLiveData.postValue(repository.getMembers())
      }catch (ex : Exception){
        Log.e("Network error", "${ex.localizedMessage}")
      }
    }
  }
}
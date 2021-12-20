package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdn.tms.android.alertingsystemhackaton.R
import com.gdn.tms.android.alertingsystemhackaton.UserDetails
import com.gdn.tms.android.alertingsystemhackaton.databinding.FragmentActiveAlertBinding
import com.gdn.tms.android.alertingsystemhackaton.databinding.FragmentNotificationBinding
import com.gdn.tms.android.alertingsystemhackaton.feature.model.NotificationModel
import com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel.DashboardActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_active_alert.loading
import kotlinx.android.synthetic.main.item_notification.btn_ack

@AndroidEntryPoint
class NotificationFragment : Fragment(), NotificationCommunicator {
  private val activityViewModel: DashboardActivityViewModel by activityViewModels()
  private lateinit var binding : FragmentNotificationBinding
  private var notificationAdapter: NotificationAdapter? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentNotificationBinding.bind(
      inflater.inflate(
        R.layout.fragment_notification, container, false
      )
    )
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
    initializeAdapter()
    binding.loading.isVisible = true
    activityViewModel.fetchNotification(UserDetails.getUserName()?:"", "APP")

  }

  private fun initializeAdapter() {
    binding.recycleView.apply {
      notificationAdapter = NotificationAdapter(mutableListOf(), this@NotificationFragment)
      this.adapter = notificationAdapter
    }
  }

  private fun observer() {
    activityViewModel.notificationLiveData.observe(viewLifecycleOwner, {
      Log.e("adapter instance", notificationAdapter.toString())
      loading.isVisible = false
      it?.let { notification ->
        notificationAdapter?.appendDataToList(notification)
      }
    })
  }

  override fun notification(notificationModel: NotificationModel) {
      activityViewModel.acceptNotification(notificationModel.id!!, "ACKNOWLEDGED")
  }
}

interface NotificationCommunicator{
  fun notification(notificationModel: NotificationModel)
}
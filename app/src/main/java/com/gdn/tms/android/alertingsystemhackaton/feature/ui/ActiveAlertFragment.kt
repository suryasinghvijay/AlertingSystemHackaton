package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdn.tms.android.alertingsystemhackaton.R
import com.gdn.tms.android.alertingsystemhackaton.databinding.FragmentActiveAlertBinding
import com.gdn.tms.android.alertingsystemhackaton.feature.AlertFragmentCommunicator
import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertDetails
import com.gdn.tms.android.alertingsystemhackaton.feature.model.NotificationModel
import com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel.DashboardActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
const val PENDING ="pending"
const val EVENT_DETAILS ="eventDetails"
@AndroidEntryPoint class ActiveAlertFragment : Fragment(), AlertFragmentCommunicator {
  private val activityViewModel: DashboardActivityViewModel by activityViewModels()
  private lateinit var binding: FragmentActiveAlertBinding
  private var activeAdapter: AlertAdapter? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentActiveAlertBinding.bind(
      inflater.inflate(
        R.layout.fragment_active_alert, container, false
      )
    )
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
    initializeAdapter()
    activityViewModel.fetchActiveAlertFromServer("abcd", 1, 10)
  }

  private fun initializeAdapter() {
    binding.recycleView.apply {
      activeAdapter = AlertAdapter(mutableListOf(), this@ActiveAlertFragment, PENDING)
      this.adapter = activeAdapter
      addItemDecoration(
        DividerItemDecoration(
          this.context,
          LinearLayoutManager.VERTICAL
        ))
    }
  }

  private fun observer() {
    activityViewModel.activeAlertLiveData.observe(viewLifecycleOwner, {
      Log.e("adapter instance", activeAdapter.toString())
      activeAdapter?.appendDataToList(it.contents.toMutableList())
    })

    activityViewModel.notificationLiveData.observe(viewLifecycleOwner, {
      generateNotification()
    })
  }

  private fun generateNotification() {
    val intent = Intent(context, DashboardActivity::class.java)
    val pendingIntent =
      PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val notificationManager =
      context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val notificationChannel =
        NotificationChannel("1", "alert", NotificationManager.IMPORTANCE_HIGH)
      notificationChannel.enableLights(true)
      notificationChannel.lightColor = Color.RED
      notificationChannel.enableVibration(false)
      notificationManager.createNotificationChannel(notificationChannel) //        .setContent(contentView)
      val builder =
        Notification.Builder(context, "1").setSmallIcon(R.drawable.ic_launcher_background)
          .setContentIntent(pendingIntent)

      notificationManager.notify(System.currentTimeMillis().toInt(), builder.build());

    } else { //        .setContent(contentView)
      val builder = Notification.Builder(context).setSmallIcon(R.drawable.ic_launcher_background)
        .setContentIntent(pendingIntent)
      notificationManager.notify(System.currentTimeMillis().toInt(), builder.build());
    }
  }

  override fun navigateToDetailsScreen(alertDetails: AlertDetails) {
    val bundle = Bundle().apply { putParcelable(EVENT_DETAILS, alertDetails) }
    Intent(requireContext(), AlertDetailsActivity::class.java).apply {
      putExtras(bundle)
      startActivity(this)
    }
  }
}
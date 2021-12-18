package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gdn.tms.android.alertingsystemhackaton.R
import com.gdn.tms.android.alertingsystemhackaton.databinding.FragmentAlertAdapterBinding
import com.gdn.tms.android.alertingsystemhackaton.databinding.ItemNotificationBinding
import com.gdn.tms.android.alertingsystemhackaton.feature.AlertFragmentCommunicator
import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertDetails
import com.gdn.tms.android.alertingsystemhackaton.feature.model.NotificationModel

class NotificationAdapter(
  private val notificationList: MutableList<NotificationModel>,
  private val communicator: NotificationCommunicator,
) : RecyclerView.Adapter<NotificationAdapter.AlertAdapterViewHolder>() {

  override fun onCreateViewHolder(
    viewGroup: ViewGroup, viewType: Int
  ) =
    AlertAdapterViewHolder(LayoutInflater.from(viewGroup.context).inflate(
      R.layout.fragment_alert_adapter, viewGroup, false
    ))

  override fun onBindViewHolder(holder: AlertAdapterViewHolder, position: Int) {

    holder.binding?.tvSentTo?.text = notificationList[position].sentTo
    holder.binding?.tvSummary?.text = notificationList[position].summary
    holder.binding?.tvDetails?.text = notificationList[position].details
    holder.binding?.tvCreated?.text = notificationList[position].createdDate
    holder.binding?.root?.setOnClickListener { communicator.notification(notificationList[holder.absoluteAdapterPosition]) }
  }

  override fun getItemCount() = notificationList.size

  class AlertAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var binding: ItemNotificationBinding? = null

    init {
      binding = ItemNotificationBinding.bind(view)
    }
  }

  fun appendDataToList(updateList: MutableList<NotificationModel>){
    notificationList.addAll(updateList)
    notifyDataSetChanged()
  }
}
package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gdn.tms.android.alertingsystemhackaton.R
import com.gdn.tms.android.alertingsystemhackaton.databinding.FragmentAlertAdapterBinding
import com.gdn.tms.android.alertingsystemhackaton.feature.AlertFragmentCommunicator
import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertDetails

class AlertAdapter(
  private val alertList: MutableList<AlertDetails>,
  private val communicator: AlertFragmentCommunicator,
  private val alertType: String
) : RecyclerView.Adapter<AlertAdapter.AlertAdapterViewHolder>() {

  override fun onCreateViewHolder(
    viewGroup: ViewGroup, viewType: Int
  ) =
    AlertAdapterViewHolder(LayoutInflater.from(viewGroup.context).inflate(
      R.layout.fragment_alert_adapter, viewGroup, false
    ))

  override fun onBindViewHolder(holder: AlertAdapterViewHolder, position: Int) {
    holder.binding?.tvEventDesc?.text = alertList[position].summary
    holder.binding?.tvEventPriority?.text = alertList[position].severity
    holder.binding?.tvEventSquad?.text = alertList[position].squad
    holder.binding?.root?.setOnClickListener { communicator.navigateToDetailsScreen(alertList[holder.absoluteAdapterPosition]) }
  }

  override fun getItemCount() = alertList.size

  class AlertAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var binding: FragmentAlertAdapterBinding? = null

    init {
      binding = FragmentAlertAdapterBinding.bind(view)
    }
  }

  fun updateAdapter(updateList: MutableList<AlertDetails>){
    alertList.apply {
      clear()
      addAll(updateList)
    }
    notifyDataSetChanged()
  }

  fun appendDataToList(updateList: MutableList<AlertDetails>){
    alertList.clear()
    alertList.addAll(updateList)
    notifyDataSetChanged()
  }
}
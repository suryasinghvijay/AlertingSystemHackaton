package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.gdn.tms.android.alertingsystemhackaton.databinding.ActivityEventDetailsLayoutBinding
import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertDetails
import com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel.DashboardActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint class AlertDetailsActivity : AppCompatActivity() {

  private lateinit var binding: ActivityEventDetailsLayoutBinding
  private val viewModel : DashboardActivityViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityEventDetailsLayoutBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val eventDetails = intent.extras?.getParcelable<AlertDetails>(EVENT_DETAILS)
    binding.apply {
      tvDate.text = eventDetails?.generatedDate
      tvDescription.text = eventDetails?.summary
      tvDetails.text = eventDetails?.details
      tvUpdatedBy.text = eventDetails?.updatedBy
      tvUpdatedBy.isVisible = !(eventDetails?.updatedBy == null)
      textView12.isVisible = !(eventDetails?.updatedBy == null)
      tvUpdatedTime.isVisible = !(eventDetails?.updatedBy == null)
      textView14.isVisible = !(eventDetails?.updatedBy == null)
      tvUpdatedTime.text = eventDetails?.updatedDate
      tvSquad.text = eventDetails?.squad
      tvPriority.text = eventDetails?.severity
      tvStatus.text = eventDetails?.status
    }

    binding.button2.setOnClickListener {
      viewModel.acceptNotification(eventDetails?.id!!, eventDetails.status)
    }
    binding.button.setOnClickListener {
      finish()
    }
  }
}
package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gdn.tms.android.alertingsystemhackaton.databinding.ActivityEventDetailsLayoutBinding
import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint class AlertDetailsActivity : AppCompatActivity() {

  private lateinit var binding: ActivityEventDetailsLayoutBinding

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
      tvUpdatedTime.text = eventDetails?.updatedDate
      tvSquad.text = eventDetails?.squad
      tvPriority.text = eventDetails?.severity
      tvStatus.text = eventDetails?.status
    }
  }

  private fun observeViewModel() {
  }
}
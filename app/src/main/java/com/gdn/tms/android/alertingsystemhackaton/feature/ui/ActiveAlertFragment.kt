package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gdn.tms.android.alertingsystemhackaton.R
import com.gdn.tms.android.alertingsystemhackaton.UserDetails
import com.gdn.tms.android.alertingsystemhackaton.databinding.FragmentActiveAlertBinding
import com.gdn.tms.android.alertingsystemhackaton.feature.AlertFragmentCommunicator
import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertDetails
import com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel.DashboardActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_active_alert.loading

const val PENDING = "pending"
const val EVENT_DETAILS = "eventDetails"

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
    binding.loading.isVisible = true
  }

  override fun onResume() {
    super.onResume()
    activityViewModel.fetchActiveAlertFromServer(UserDetails.getSquadName()?:"", 0, 50, "OPEN")
  }

  private fun initializeAdapter() {
    binding.recycleView.apply {
      activeAdapter = AlertAdapter(mutableListOf(), this@ActiveAlertFragment, PENDING)
      this.adapter = activeAdapter
    }
  }

  private fun observer() {
    activityViewModel.activeAlertLiveData.observe(viewLifecycleOwner, {
      loading.isVisible = false
      Log.e("adapter instance", activeAdapter.toString())
      activeAdapter?.appendDataToList(it.contents.toMutableList())
    })


  }

  override fun navigateToDetailsScreen(alertDetails: AlertDetails) {
    val bundle = Bundle().apply { putParcelable(EVENT_DETAILS, alertDetails) }
    Intent(requireContext(), AlertDetailsActivity::class.java).apply {
      putExtras(bundle)
      startActivity(this)
    }
  }
}
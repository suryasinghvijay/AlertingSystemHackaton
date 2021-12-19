package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gdn.tms.android.alertingsystemhackaton.R
import com.gdn.tms.android.alertingsystemhackaton.UserDetails
import com.gdn.tms.android.alertingsystemhackaton.databinding.FragmentActiveAlertBinding
import com.gdn.tms.android.alertingsystemhackaton.feature.AlertFragmentCommunicator
import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertDetails
import com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel.DashboardActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

const val DONE = "done"

@AndroidEntryPoint class DoneAlertFragment : Fragment(), AlertFragmentCommunicator {
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
    activityViewModel.fetchActiveAlertFromServer(UserDetails.getSquadName()?:"", 0, 50, "ACKNOWLEDGED")
  }

  private fun initializeAdapter() {
    binding.recycleView.apply {
      activeAdapter = AlertAdapter(mutableListOf(), this@DoneAlertFragment, DONE)
      this.adapter = activeAdapter
    }
  }

  private fun observer() {
    activityViewModel.pastAlertLiveData.observe(viewLifecycleOwner, {
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
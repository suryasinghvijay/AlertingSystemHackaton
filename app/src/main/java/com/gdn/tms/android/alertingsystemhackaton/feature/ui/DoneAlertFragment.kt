package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gdn.tms.android.alertingsystemhackaton.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint class DoneAlertFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? { // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_done_alert, container, false)
  }
}
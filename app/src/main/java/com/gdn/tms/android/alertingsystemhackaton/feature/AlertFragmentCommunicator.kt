package com.gdn.tms.android.alertingsystemhackaton.feature

import com.gdn.tms.android.alertingsystemhackaton.feature.model.AlertDetails

interface AlertFragmentCommunicator {
  fun navigateToDetailsScreen(alertDetails: AlertDetails)
}
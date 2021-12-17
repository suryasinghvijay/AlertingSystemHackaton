package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.gdn.tms.android.alertingsystemhackaton.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.bottom_navigation
import kotlinx.android.synthetic.main.activity_dashboard.toolbar

@AndroidEntryPoint class DashboardActivity : AppCompatActivity() {
  private lateinit var navController: NavController
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_dashboard)
    setUpNavigationController()
  }

  private fun setUpNavigationController() {
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setDisplayShowTitleEnabled(true)
    }
    val navHostFragment: NavHostFragment = supportFragmentManager
      .findFragmentById(R.id.nav_host_dashboard) as NavHostFragment
    navController = navHostFragment.navController
    setupActionBarWithNavController(navController)
    NavigationUI.setupWithNavController(bottom_navigation, navController)
//    navController.addOnDestinationChangedListener { _, destination, _ ->
//      when (destination.id) {
//        R.id.lineHaulInwardScanFragment -> {
//          binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_left)
//        }
//      }
//    }
  }
}
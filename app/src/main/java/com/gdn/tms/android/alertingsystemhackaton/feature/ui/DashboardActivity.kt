package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.gdn.tms.android.alertingsystemhackaton.R
import com.gdn.tms.android.alertingsystemhackaton.databinding.ActivityDashboardBinding
import com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel.DashboardActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.bottom_navigation

@AndroidEntryPoint class DashboardActivity : AppCompatActivity() {
  private lateinit var navController: NavController
  private lateinit var binding: ActivityDashboardBinding
  private val viewModel : DashboardActivityViewModel by viewModels()
  private var handler :Handler? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDashboardBinding.inflate(layoutInflater)
    handler = Handler()
    setContentView(binding.root)
    setUpNavigationController()
    setUpWorkManager()
  }

  private fun setUpWorkManager() {
    handler?.postDelayed(object : Runnable {
      override fun run() {
        Log.e("workmanager", "workmanager")
       viewModel.fetchNotification("asd","asd")
        handler?.postDelayed(this, 200)
      }
    }, 10)
  }

  private fun setUpNavigationController() {
    setSupportActionBar(binding.toolbar)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setDisplayShowTitleEnabled(true)
    }
    val navHostFragment: NavHostFragment =
      supportFragmentManager.findFragmentById(R.id.nav_host_dashboard) as NavHostFragment
    navController = navHostFragment.navController
    setupActionBarWithNavController(navController)
    NavigationUI.setupWithNavController(
      bottom_navigation,
      navController
    ) //    navController.addOnDestinationChangedListener { _, destination, _ ->
    //      when (destination.id) {
    //        R.id.lineHaulInwardScanFragment -> {
    //          binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_left)
    //        }
    //      }
    //    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.home_menu, menu);
    return true
  }

  override fun onDestroy() {
    handler =  null
    super.onDestroy()
  }
}
package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
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
        handler?.postDelayed(this, 20000)
      }
    }, 1000)
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
    val appBarConfiguration = AppBarConfiguration(setOf(R.id.pending_alert, R.id.done_alert))
    NavigationUI.setupWithNavController(
      bottom_navigation,
      navController
    )
    binding.toolbar.setupWithNavController(navController, appBarConfiguration)
      navController.addOnDestinationChangedListener { _, destination, _ ->
          when (destination.id) {
            R.id.notificationFragment -> {
              binding.bottomNavigation.isVisible = false
              binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_left)
            }
            else ->{
              binding.bottomNavigation.isVisible = true
            }
          }
        }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.home_menu, menu);
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle item selection
    return when (item.getItemId()) {
      R.id.notification -> {
        navController.navigate(R.id.notificationFragment)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onDestroy() {
    handler =  null
    super.onDestroy()
  }
}
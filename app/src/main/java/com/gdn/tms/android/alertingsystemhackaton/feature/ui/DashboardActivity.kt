package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
import com.gdn.tms.android.alertingsystemhackaton.UserDetails
import com.gdn.tms.android.alertingsystemhackaton.databinding.ActivityDashboardBinding
import com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel.DashboardActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.bottom_navigation
import android.content.ContentResolver
import android.media.AudioAttributes
import android.net.Uri
import android.media.RingtoneManager

import java.lang.Exception

@AndroidEntryPoint class DashboardActivity : AppCompatActivity() {
  private lateinit var navController: NavController
  private lateinit var binding: ActivityDashboardBinding
  private val viewModel : DashboardActivityViewModel by viewModels()
  private var handler :Handler? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.e("userDetails", UserDetails.toString())
    binding = ActivityDashboardBinding.inflate(layoutInflater)
    handler = Handler()
    setContentView(binding.root)
    setUpNavigationController()
    setUpWorkManager()
    observeViewModel()
  }

  private fun observeViewModel() {
    viewModel.notificationLiveData.observe(this, {
      generateNotification(it.size ?: 1)
    })
  }

  private fun generateNotification(size: Int) {
    if (size == 0) return
    //playNotificationSound()
    val intent = Intent(this, DashboardActivity::class.java)
    val pendingIntent =
      PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val notificationManager =
      this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val soundUri =
      Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + R.raw.police_siren)
    Log.e("sound", soundUri.toString())
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val notificationChannel =
        NotificationChannel("1", "alert", NotificationManager.IMPORTANCE_HIGH)
      notificationChannel.enableLights(true)
      notificationChannel.lightColor = Color.RED
      notificationChannel.enableVibration(false)
      val audioAttributes =
        AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
          .setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
      notificationChannel.setSound(soundUri, audioAttributes)
      notificationManager.createNotificationChannel(notificationChannel)
      val builder =
        Notification.Builder(this, "1").setSmallIcon(R.drawable.ic_launcher_background)
          .setContentIntent(pendingIntent).setContentTitle( "Alert Notification" )
          .setContentText( "$size or more service needs your attention" )

      notificationManager.notify(1.toInt(), builder.build());

    } else { //        .setContent(contentView)
      val builder = Notification.Builder(this).setSmallIcon(R.drawable.ic_launcher_background)
        .setContentIntent(pendingIntent).setSound(soundUri)
      notificationManager.notify(1.toInt(), builder.build());
    }
  }

  private fun playNotificationSound() {
    try {
      val alarmSound = Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/raw/police_siren")
      val r = RingtoneManager.getRingtone(applicationContext, alarmSound)
      r.play()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun setUpWorkManager() {
    handler?.postDelayed(object : Runnable {
      override fun run() {
        Log.e("workmanager", "workmanager")
       viewModel.fetchNotification(UserDetails.getUserName()?:"","APP")
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
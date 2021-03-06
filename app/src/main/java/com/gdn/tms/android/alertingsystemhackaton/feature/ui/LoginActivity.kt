package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.gdn.tms.android.alertingsystemhackaton.HOSTNAME
import com.gdn.tms.android.alertingsystemhackaton.R
import com.gdn.tms.android.alertingsystemhackaton.UserDetails
import com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel.MembersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_login.bt_submit
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_login.host_name
import kotlinx.android.synthetic.main.activity_login.loading

@AndroidEntryPoint class LoginActivity : AppCompatActivity() {

  private val mViewModel : MembersViewModel by viewModels()
  private var hubName :String? = null
  @Inject lateinit var preferences:SharedPreferences
  val members = arrayListOf<String>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    if (preferences.getBoolean("is_logged_in", false)){
      finish()
      startActivity(Intent(this, DashboardActivity::class.java))
    }

    mViewModel.getMembers()
    observe()

    btn_login.setOnClickListener {
      if (et_password.text.isNullOrEmpty()){
        Toast.makeText(this, "Please enter the credentials", Toast.LENGTH_SHORT).show()
      }else{
        loading.isVisible = true
        mViewModel.fetchSquadDetails(et_email.text.toString())
      }
    }

    et_email.setOnClickListener {
      et_email.requestFocus()
    }

    et_email.setOnItemClickListener { parent, view, position, id ->
      val userName = parent.getItemAtPosition(position).toString()
     et_password.setText(userName)
      mViewModel.membersLiveData.value?.first { it.name == userName }?.let {
        UserDetails.updateUserDetails(
          it
        )
      }
    }
    bt_submit.setOnClickListener {
      members.clear()
      val ipDetails = host_name.text
      preferences.edit().putString(HOSTNAME, "http://$ipDetails/").commit()
      loading.isVisible = true
      mViewModel.getMembers()
    }
  }

  private fun observe(){
    mViewModel.membersLiveData.observe(this, { member ->
      members.clear()
      loading.isVisible = false
      if (member.any()) {
        member.forEach {
          it.name?.let { it1 -> members.add(it1) }
        }
        val arrayAdapter =
          ArrayAdapter(this, R.layout.members_list, R.id.tv_hub_name, members)
        et_email.setAdapter(arrayAdapter)
      }
    })

    mViewModel.squadDetailsLiveData.observe(this, {
      loading.isVisible = false
      UserDetails.updateSquadDetails(it)
      finish()
      this.startActivity(
        Intent(this, DashboardActivity::class.java).setFlags(
          Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        ))
      preferences.edit().putBoolean("is_logged_in", true).apply()
    })
  }
}
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
import com.gdn.tms.android.alertingsystemhackaton.R
import com.gdn.tms.android.alertingsystemhackaton.UserDetails
import com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel.MembersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_login.loading

@AndroidEntryPoint class LoginActivity : AppCompatActivity() {

  private val mViewModel : MembersViewModel by viewModels()
  private var hubName :String? = null
  private var sharedPref : SharedPreferences? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

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
  }

  private fun observe(){
    val members = arrayListOf<String>()
    mViewModel.membersLiveData.observe(this, { member ->
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
      startActivity(Intent(this, DashboardActivity::class.java))
      sharedPref = getPreferences(Context.MODE_PRIVATE)
      with (sharedPref?.edit()) {
        this?.putBoolean("is_logged_in", true)
        this?.apply()
      }
    })
  }
}
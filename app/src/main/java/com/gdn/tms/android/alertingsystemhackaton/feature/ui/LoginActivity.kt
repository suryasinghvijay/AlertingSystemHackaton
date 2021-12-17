package com.gdn.tms.android.alertingsystemhackaton.feature.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.gdn.tms.android.alertingsystemhackaton.MainActivity
import com.gdn.tms.android.alertingsystemhackaton.R
import com.gdn.tms.android.alertingsystemhackaton.feature.viewmodel.MembersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.autocomplete_hub_search
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.et_password

@AndroidEntryPoint class LoginActivity : AppCompatActivity() {

  private val mViewModel : MembersViewModel by viewModels()
  private var hubName :String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    mViewModel.getMembers()
    observe()

    btn_login.setOnClickListener {
      if (et_password.text.isNullOrEmpty()){
        Toast.makeText(this, "Please enter the credentials", Toast.LENGTH_SHORT).show()
      }else{
        startActivity(Intent(this, DashboardActivity::class.java))
      }
    }

    autocomplete_hub_search.setOnClickListener {
      autocomplete_hub_search.requestFocus()
    }

    autocomplete_hub_search.setOnItemClickListener { parent, view, position, id ->
      hubName = parent.getItemAtPosition(position).toString()
      Log.e("",parent.toString())
    }
  }

  private fun observe(){
    val members = arrayListOf<String>()
    mViewModel.membersLiveData.observe(this, { member ->
      if (member.any()) {
        member.forEach {
          members.add(it.name)
        }
        val arrayAdapter =
          ArrayAdapter(this, R.layout.members_list, R.id.tv_hub_name, members)
        autocomplete_hub_search.setAdapter(arrayAdapter)
      }
    })
  }
}
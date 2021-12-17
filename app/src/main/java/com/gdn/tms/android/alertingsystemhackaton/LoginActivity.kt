package com.gdn.tms.android.alertingsystemhackaton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_password

class LoginActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    btn_login.setOnClickListener {
      if (et_email.text.isNullOrEmpty() || et_password.text.isNullOrEmpty()){
        Toast.makeText(this, "Please enter the credentials", Toast.LENGTH_SHORT).show()
      }else{
        startActivity(Intent(this, MainActivity::class.java))
      }
    }
  }
}
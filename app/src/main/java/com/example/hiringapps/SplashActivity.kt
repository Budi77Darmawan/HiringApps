package com.example.hiringapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.example.hiringapps.databinding.ActivitySplashBinding
import com.example.hiringapps.onboard.OnBoardActivity
import com.example.hiringapps.regislogin.LoginActivity
import com.example.hiringapps.sharedpref.Constant
import com.example.hiringapps.sharedpref.SharedPrefProvider

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var i: Intent
    private lateinit var sharedPref: SharedPrefProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        sharedPref = SharedPrefProvider(applicationContext)

        Handler().postDelayed(
            {
                val sharedPrefLogin = sharedPref.getBoolean(Constant.KEY_REMEMBER)
                val sharedPrefOnBoard = sharedPref.getBoolean(Constant.KEY_ONBOARD)
                i = if (sharedPrefLogin && sharedPrefOnBoard) {
                    Intent(this, MainActivity::class.java)
                } else if (!sharedPrefLogin && sharedPrefOnBoard) {
                    Intent(this, LoginActivity::class.java)
                } else {
                    Intent(this, OnBoardActivity::class.java)
                }
                startActivity(i)
                finish()

            },
            2000
        )
    }
}

package com.example.travelmania.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.travelmania.bottomnavmain.MainActivity
import com.example.travelmania.R
import com.example.travelmania.onboarding.OnBoardingActivity
import com.example.travelmania.usermanagment.LoginActivity
import com.example.travelmania.usermanagment.register.Register


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            if(onBoardingFinished()){
                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }

            finish()
        }, 2000)


    }
    fun onBoardingFinished():Boolean{
        val sharedPref = getSharedPreferences("onBoarding",Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }
}
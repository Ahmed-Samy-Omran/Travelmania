package com.example.travelmania.onboarding

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.travelmania.R
import com.example.travelmania.onboarding.screens.FirstOnBoard
import com.example.travelmania.onboarding.screens.SecondOnBoard
import com.example.travelmania.onboarding.screens.ThirdOnBoard
import me.relex.circleindicator.CircleIndicator
import me.relex.circleindicator.CircleIndicator3

class OnBoardingActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager2
    lateinit var indicator: CircleIndicator3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        val indicatorColors = arrayListOf(R.color.not_yellow,R.color.purple,R.color.green_button)
        val fragmentlist= arrayListOf<Fragment>(
            FirstOnBoard(),
            SecondOnBoard(),
            ThirdOnBoard()
        )
        val adapter= ViewPagerAdapter(
            fragmentlist,
            supportFragmentManager,
            lifecycle
        )
        viewPager= findViewById(R.id.viewpager_onboarding)
        viewPager.adapter=adapter
        indicator = findViewById(R.id.onboard_indicator)
        indicator.setViewPager(viewPager)


    }
}
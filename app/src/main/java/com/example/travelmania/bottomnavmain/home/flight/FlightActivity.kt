package com.example.travelmania.bottomnavmain.home.flight

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.tablayout.adapter.ViewPagerAdapter
import com.example.travelmania.bottomnavmain.MainActivity
import com.example.travelmania.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FlightActivity : AppCompatActivity() {
    lateinit var back_press: ImageView

    // source of data
    val tabsArray = arrayOf("Return", "One Way", "Multi-City")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flight_activity)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tablayout = findViewById<TabLayout>(R.id.tablayout)
        back_press = findViewById(R.id.arrow_left)
        back_press.setOnClickListener {
           Intent(this, MainActivity::class.java).also {
               startActivity(it)
           }
        }
        // This code applies a custom font to each tab in the TabLayout using a TypefaceSpan and a custom font file.
        for (i in tabsArray.indices) {
            val tab = tablayout.getTabAt(i)
            val text = SpannableString(tabsArray[i])
            text.setSpan(TypefaceSpan("quicksandmedium.ttf"), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            tab?.text = text
        }

        //instance of adapter
        val my_adapter= ViewPagerAdapter(
            supportFragmentManager,
            lifecycle
        )

        //link viewPager with adapter
        viewPager.adapter=my_adapter
        //untill here this how to link adapter with the viewPager

        // this how to link tabLayout with ViewPager i use TabLayoutMediator
        // TabLayoutMediator IS RESPONSIBLE FOR connecting tabLayout with the array i created at first and text inside will be  the title inside tabs


        // if user click on the first tab it will display return fragment
        TabLayoutMediator(tablayout, viewPager) {
                tab, position -> tab.text = tabsArray[position]
        }.attach()
    }
}
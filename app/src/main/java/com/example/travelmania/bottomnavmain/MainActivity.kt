package com.example.travelmania.bottomnavmain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.bookingtrips.BookingFragment
import com.example.travelmania.bottomnavmain.favourites.FragmentFavourites
import com.example.travelmania.bottomnavmain.home.HomeFragment
import com.example.travelmania.bottomnavmain.profile.FragmentProfile

class MainActivity : AppCompatActivity() {
    // source of data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val bottomNavigationView:BottomNavigationView = findViewById(R.id.navigation_main)


        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_Home -> {
                    makeCurrentFragment(HomeFragment())
                }

                 R.id.item_favorite -> {
                    makeCurrentFragment(FragmentFavourites())
                }
                R.id.item_booking -> {
                    makeCurrentFragment(BookingFragment())
                }
                R.id.item_profile -> {
                    makeCurrentFragment(FragmentProfile())
                }
            }
            true
        }
        bottomNavigationView.selectedItemId = R.id.item_Home



    }

    // make fragment transaction fun
    private fun makeCurrentFragment(fragment: Fragment): FragmentTransaction {
        return supportFragmentManager.beginTransaction().apply {
            replace(R.id.navigation_frame_layout, fragment)
            commit()
        }
    }
}
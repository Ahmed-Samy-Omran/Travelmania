package com.example.tablayout.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.travelmania.bottomnavmain.home.flight.multicity.MultiCity_Fragment
import com.example.travelmania.bottomnavmain.home.flight.oneway.OneWay_Fragment
import com.example.travelmania.bottomnavmain.home.flight.returnflight.ReturnFragment

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3    // number of tabs

    }

    override fun createFragment(position: Int): Fragment {
        when(position){   //this how to check the postion of tabs

            0 -> return ReturnFragment()
            1 -> return OneWay_Fragment()

        }
        return MultiCity_Fragment()


    }
}
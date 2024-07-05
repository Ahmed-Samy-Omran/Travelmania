package com.example.travelmania.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.travelmania.R

class SecondOnBoard :Fragment() {
    lateinit var next_second: LinearLayout
    lateinit var viewPager2: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_onboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next_second= requireView().findViewById(R.id.next_2)
        viewPager2=requireActivity().findViewById(R.id.viewpager_onboarding)
        next_second.setOnClickListener {
            viewPager2.currentItem = 2
        }
    }
}
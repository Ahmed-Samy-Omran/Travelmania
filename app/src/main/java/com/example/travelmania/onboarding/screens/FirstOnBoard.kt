package com.example.travelmania.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.travelmania.R

class FirstOnBoard :Fragment() {
    lateinit var next_first:LinearLayout
    lateinit var viewPager2: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_onboard_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next_first= requireView().findViewById(R.id.next_1)
        viewPager2=requireActivity().findViewById(R.id.viewpager_onboarding)
        next_first.setOnClickListener {
        viewPager2.currentItem = 1
        }
    }
}
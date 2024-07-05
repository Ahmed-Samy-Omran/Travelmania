package com.example.travelmania.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.travelmania.R
import com.example.travelmania.usermanagment.LoginActivity

class ThirdOnBoard :Fragment() {
    lateinit var get_Started: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.third_onboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        get_Started=requireView().findViewById(R.id.get_started)
        get_Started.setOnClickListener {
            val intent= Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)
            onBoardingFinished()
        }
    }
    fun onBoardingFinished(){
        val sharedPref=requireActivity().getSharedPreferences("onBoarding",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }
}
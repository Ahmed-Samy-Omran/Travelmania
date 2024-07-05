package com.example.travelmania.bottomnavmain.bookingtrips

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.bookingtrips.active.FragmentActiveTrips
import com.example.travelmania.bottomnavmain.bookingtrips.cancelled.FragmentCancelledTrips
import com.example.travelmania.bottomnavmain.bookingtrips.past.FragmentPastTrips

class BookingFragment: Fragment(){
    lateinit var active:AppCompatButton
    lateinit var past:AppCompatButton
    lateinit var cancel:AppCompatButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        active=requireView().findViewById(R.id.active_btn)
        past=requireView().findViewById(R.id.past_btn)
        cancel = requireView().findViewById(R.id.cancel_btn)
        active.setOnClickListener {
            makeCurrentFragment(FragmentActiveTrips())
        }
        past.setOnClickListener {
            makeCurrentFragment(FragmentPastTrips())
        }
        cancel.setOnClickListener {
            makeCurrentFragment(FragmentCancelledTrips())
        }
    }

    private fun makeCurrentFragment(fragment: Fragment): FragmentTransaction {
        return requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.booking_Frame, fragment)
            commit()
        }
    }
}
package com.example.travelmania.bottomnavmain.home.flight.oneway

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.travelmania.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class OneWay_Fragment : Fragment() {
    lateinit var frameDatesOneWay: View
    lateinit var selectDateOneWayTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_one_way_, container, false)
        frameDatesOneWay=view.findViewById(R.id.frame_dates_oneway)
        selectDateOneWayTV=view.findViewById(R.id.select_dates_oneway)


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        frameDatesOneWay.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, monthOfYear)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)

                    selectDateOneWayTV.text = formattedDate
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        return view
    }



    }

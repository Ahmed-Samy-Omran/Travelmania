package com.example.travelmania.bottomnavmain.home.hotel

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.travelmania.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HotelsActivityMain: AppCompatActivity() {

    lateinit var enterDatesLinear: LinearLayout
    lateinit var calenderTV: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotels_mains)

        enterDatesLinear=findViewById(R.id.enter_dates)
        calenderTV=findViewById(R.id.calender_TV)

        val calendar= Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        enterDatesLinear.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@HotelsActivityMain,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, monthOfYear)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)

                    calenderTV.text = formattedDate
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

}
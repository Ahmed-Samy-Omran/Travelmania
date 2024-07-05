package com.example.travelmania.bottomnavmain.home.carrental

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CarRental : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    lateinit var fromDateLinear: LinearLayout
    lateinit var toDateLinear: LinearLayout
    lateinit var fromDate_TV: TextView
    lateinit var ToDate_TV: TextView
    lateinit var back_press:LinearLayout
lateinit var car_search:FloatingActionButton
    lateinit var fromClockLinear: LinearLayout
     lateinit var fromClock_TV: TextView

    lateinit var toClockLinear: LinearLayout
    lateinit var toClock_TV: TextView
    lateinit var car_rental_text:EditText
    private var isFromTimePicker = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_rental)

        fromDateLinear=findViewById(R.id.from_calender)
        toDateLinear=findViewById(R.id.to_calender)
        fromDate_TV=findViewById(R.id.fromDate_TV)
        ToDate_TV=findViewById(R.id.toDate_TV)
        car_rental_text=findViewById(R.id.text_car_rental)
        car_search=findViewById(R.id.car_rental_search)
        back_press=findViewById(R.id.car_rental_back_press)
        back_press.setOnClickListener {
          val intent=  Intent(this, MainActivity::class.java)
              startActivity(intent)

        }


        fromClockLinear=findViewById(R.id.from_clock)
        fromClock_TV=findViewById(R.id.from_clock_TV)

        toClockLinear=findViewById(R.id.to_clock)
        toClock_TV=findViewById(R.id.to_clock_TV)

        val calendar= Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
          car_search.setOnClickListener {
              val intent=Intent(this,SearchCarResult::class.java)
              intent.putExtra("Rental_Text",car_rental_text.text.toString())
              startActivity(intent)
          }
        fromDateLinear.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@CarRental,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, monthOfYear)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)

                    fromDate_TV.text = formattedDate
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }


        toDateLinear.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@CarRental,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, monthOfYear)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)

                    ToDate_TV.text = formattedDate
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

       fromClockLinear.setOnClickListener {
           isFromTimePicker = true
           showTimePickerDialog()
       }

        toClockLinear.setOnClickListener{
            isFromTimePicker = false
            showTimePickerDialogToClock()
        }


    }

    private fun showTimePickerDialogToClock() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            this, // Pass the instance of OnTimeSetListener here
            hour,
            minute,
            false
        )
        timePickerDialog.show()

    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            this, // Pass the instance of OnTimeSetListener here
            hour,
            minute,
            false
        )

        timePickerDialog.show()
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val selectedTime = formatTime(hourOfDay, minute)

        if (isFromTimePicker) {
            fromClock_TV.text = selectedTime
        } else {
            toClock_TV.text = selectedTime
        }






    }

    private fun formatTime(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        // Format the time using SimpleDateFormat
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(calendar.time)
    }


}
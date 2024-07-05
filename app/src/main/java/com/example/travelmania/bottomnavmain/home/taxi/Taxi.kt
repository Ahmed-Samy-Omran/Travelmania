package com.example.travelmania.bottomnavmain.home.taxi

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.example.travelmania.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Taxi : AppCompatActivity() {
    lateinit var fromDateTaxiLinear: LinearLayout
    lateinit var fromDateTaxiTV: TextView
    lateinit var search_icon:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi)

        fromDateTaxiLinear=findViewById(R.id.calender_attraction)
        fromDateTaxiTV=findViewById(R.id.fromDate_taxi_TV)
        search_icon= findViewById(R.id.search_taxi)


        val calendar= Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        search_icon.setOnClickListener {
            val intent = Intent(this,TaxiDetails::class.java)
            startActivity(intent)
        }
        fromDateTaxiLinear.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@Taxi,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, monthOfYear)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)

                    fromDateTaxiTV.text = formattedDate
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }
}
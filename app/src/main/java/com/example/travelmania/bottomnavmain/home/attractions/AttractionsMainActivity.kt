package com.example.travelmania.bottomnavmain.home.attractions

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.example.travelmania.bottomnavmain.MainActivity
import com.example.travelmania.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AttractionsMainActivity : AppCompatActivity() {
    lateinit var back_press:LinearLayout
    lateinit var DateAtractionsLinear: LinearLayout
    lateinit var DateAttractionsTV: TextView
    lateinit var Search_attract: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attractions_mains)


        back_press = findViewById(R.id.back_press_attractions)
        Search_attract= findViewById(R.id.search_attractions)
        Search_attract.setOnClickListener {
            val intent=Intent(this,AtractionSearchActivity::class.java)
            startActivity(intent)
        }
        back_press.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        DateAtractionsLinear=findViewById(R.id.calender_attraction)
        DateAttractionsTV=findViewById(R.id.Date_attraction_TV)

        val calendar= Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DateAtractionsLinear.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@AttractionsMainActivity,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, monthOfYear)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)

                    DateAttractionsTV.text = formattedDate
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }


    }
}
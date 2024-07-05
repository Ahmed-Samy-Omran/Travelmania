package com.example.travelmania.usermanagment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.MainActivity

class PhoneNum : AppCompatActivity() {
 lateinit var phone_number_confirm:AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)
        // Array of country codes
         phone_number_confirm=findViewById(R.id.btn_confirm_phone_number)
        phone_number_confirm.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val countryCodes = resources.getStringArray(R.array.country_codes)

        val autoCompleteTextView: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        val adapter = AutoCompleteAdapter(this, R.layout.dropdown_item, countryCodes)
        autoCompleteTextView.setAdapter(adapter)

        // Set a click listener for the arrow icon
        val arrowIcon: ImageView = findViewById(R.id.arrowIcon)
        arrowIcon.setOnClickListener {
            autoCompleteTextView.showDropDown()
        }

        // Set an item click listener for the dropdown
        autoCompleteTextView.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->

            val selectedCountryCode = adapter.getItem(position)
//            Toast.makeText(this, "Selected country code: $selectedCountryCode", Toast.LENGTH_SHORT).show()
        }

    }
}
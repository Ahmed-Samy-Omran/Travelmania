package com.example.travelmania.usermanagment.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.get
import com.example.travelmania.Api.ApiManager
import com.example.travelmania.Api.RegisterResponse
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.MainActivity
import com.example.travelmania.usermanagment.AutoCompleteAdapter
import com.example.travelmania.usermanagment.LoginActivity
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    lateinit var sign_up:AppCompatButton
    lateinit var login_text:LinearLayout
    lateinit var first_name:EditText
    lateinit var password:EditText
    lateinit var phone_number:EditText
    lateinit var age:EditText
    lateinit var e_mail:EditText
    lateinit var lastname:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
          sign_up=findViewById(R.id.btn_sign_up)
            login_text=findViewById(R.id.login_txt)
           first_name=findViewById(R.id.input_first_name)
            password=findViewById(R.id.input_password)
            phone_number=findViewById(R.id.input_phone_number)
            age=findViewById(R.id.input_age)
            e_mail=findViewById(R.id.input_email)
            lastname=findViewById(R.id.input_last_name)

        sign_up.setOnClickListener {
            val registerResponse =RegisterResponse(first_name.text.toString(),password.text.toString()
                ,phone_number.text.toString(),age.text.toString().toInt(),e_mail.text.toString(),lastname.text.toString())
            ApiManager.getApis().register(registerResponse)
                .enqueue(object: Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                         if(response.isSuccessful){
                             if(response.body()!=null){
                                 val intent = Intent(this@Register, LoginActivity::class.java)
                                 startActivity(intent)
                             }
                         }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

                    }

                })

        }
        login_text.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        // Array of country codes
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
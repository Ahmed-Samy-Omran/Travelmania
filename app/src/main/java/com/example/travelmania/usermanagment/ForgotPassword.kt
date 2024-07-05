package com.example.travelmania.usermanagment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.travelmania.R

class ForgotPassword : AppCompatActivity() {
    lateinit var reset_pass:TextView
    lateinit var req_code:AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_passord)
        reset_pass=findViewById(R.id.rest)
        reset_pass.setOnClickListener {
            val intent = Intent(this, PhoneNum::class.java)
            startActivity(intent)
        }
        req_code=findViewById(R.id.btn_req_code)
        req_code.setOnClickListener {
            val intent = Intent(this, ConfirmNumber::class.java)
            startActivity(intent)
        }
    }
}
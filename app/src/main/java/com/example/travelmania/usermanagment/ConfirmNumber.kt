package com.example.travelmania.usermanagment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.MainActivity

class ConfirmNumber : AppCompatActivity() {
    lateinit var confirm_code: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_number)
        confirm_code = findViewById(R.id.btn_confirm)
        confirm_code.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
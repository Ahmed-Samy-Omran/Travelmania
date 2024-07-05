package com.example.travelmania.bottomnavmain.home.chatbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.travelmania.bottomnavmain.MainActivity
import com.example.travelmania.R

class ChatBot : AppCompatActivity() {
    lateinit var back_press:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)
        back_press = findViewById(R.id.chat_bot_back_press)
        back_press.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}
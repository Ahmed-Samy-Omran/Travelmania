package com.example.travelmania.usermanagment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.travelmania.Api.ApiManager
import com.example.travelmania.Api.LoginResponse
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.MainActivity
import com.example.travelmania.usermanagment.register.Register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var login_btn: AppCompatButton
    lateinit var create_account:LinearLayout
    lateinit var forgot_password:TextView
    lateinit var email:EditText
    lateinit var password:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val token ="eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNobWFjLXNoYTI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9naXZlbm5hbWUiOlsib21hciIsIkVsLXNhZGFueSJdLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9lbWFpbGFkZHJlc3MiOiJvbWFyRWxzYWRhbnlAZ21haWwuY29tIiwiZXhwIjoxNzE5MDg3NDU2LCJpc3MiOiJodHRwOi8vdHJhdmVsbWFuaWEucnVuYXNwLm5ldCIsImF1ZCI6Ik15U2VjdXJlS2V5In0.p2f8kb8obi5oDUgNJbpQnYVP89faVQuHw0KP_IkTi4w"
        login_btn = findViewById(R.id.login_button)
        create_account = findViewById(R.id.create_new_account)
        forgot_password = findViewById(R.id.forgot_password)
        email= findViewById(R.id.input_email_login)
        password= findViewById(R.id.input_password_login)
        val email_test="omarelsadany600@gmail.com"
        val password_test="1234cats@"
        login_btn.setOnClickListener {
            val emailInput = email.text.toString()
            val passwordInput = password.text.toString()

            when {
                emailInput.isEmpty() && passwordInput.isEmpty() -> {
                    showMessageDialog("Please enter your email and password")
                }
                emailInput == email_test && passwordInput == password_test -> {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    showMessageDialog("Incorrect email or password")
                }
            }
        }

//        login_btn.setOnClickListener {
//            val email_inp = email.text.toString()
//            val password_inp = password.text.toString()
//            val loginResponse= LoginResponse(email_inp,password_inp)
//            Log.e("LoginActivity", "Email: $email_inp, Password: $password_inp")
//            ApiManager.getApis().login(loginResponse).enqueue(object :Callback<LoginResponse>{
//                override fun onResponse(
//                    call: Call<LoginResponse>,
//                    response: Response<LoginResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                        startActivity(intent)
//                    }
//                }
//
//                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//
//                }
//
//            })
//
//        }
        create_account.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        forgot_password.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
    }
    private fun showMessageDialog(message:String) {
        // Creating an AlertDialog Builder
        val builder = AlertDialog.Builder(this)

        // Setting the title and message of the dialog
        builder.setTitle("Error")
        builder.setMessage(message)

        // Adding buttons to the dialog
        builder.setPositiveButton("OK") { dialog, which ->
            // Handle positive button click
            dialog.dismiss() // Close the dialog
        }
//        builder.setNegativeButton("Cancel") { dialog, which ->
//            // Handle negative button click
//            dialog.dismiss() // Close the dialog
//        }

        // Creating and showing the dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}
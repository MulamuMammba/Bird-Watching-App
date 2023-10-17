package com.am.bird_watching_app.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.am.bird_watching_app.R
import com.am.bird_watching_app.auth.Register

class LogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("username", "empty")
        val savedPassword = sharedPreferences.getString("password", "empty")

        val username = findViewById<EditText>(R.id.login_email)
        val password = findViewById<EditText>(R.id.login_password)

        val button = findViewById<Button>(R.id.signin_button)
        button.setOnClickListener{
                val enteredUsername = username.text.toString()
                val enteredPassword = password.text.toString()

                if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                    Toast.makeText(this, "Don't leave anything blank please", Toast.LENGTH_SHORT).show()
                } else if (enteredUsername == savedUsername && enteredPassword == savedPassword) {
                    startActivity(Intent(this, Settings::class.java))
                } else {
                    Toast.makeText(this, "Username or password is incorrect", Toast.LENGTH_SHORT).show()
                }
        }

        val forgotPassword = findViewById<TextView>(R.id.login_forgotPassword)
        forgotPassword.setOnClickListener{
            startActivity(Intent(this, forgotPassword::class.java))
        }

        val signUp = findViewById<TextView>(R.id.signup_button)
        signUp.setOnClickListener{
            startActivity(Intent(this, Register::class.java))
        }

        findViewById<TextView>(R.id.wlcome).setOnClickListener{
            startActivity(Intent(this, BirdList::class.java))
        }
    }
}
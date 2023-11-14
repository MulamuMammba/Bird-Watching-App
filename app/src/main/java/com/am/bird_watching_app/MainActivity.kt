package com.am.bird_watching_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.am.bird_watching_app.ui.onBoarding.Slide1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val firstTimeCheck = sharedPreferences.getString("firstTime?", "true")

        if (firstTimeCheck == "true") {
            startActivity(Intent(this, Slide1::class.java))
            val myEdit = sharedPreferences.edit()
            myEdit.putString("firstTime?", "false")
            myEdit.apply()
            finish()
        } else {
            startActivity(Intent(this, Login::class.java))
        }

    }

}
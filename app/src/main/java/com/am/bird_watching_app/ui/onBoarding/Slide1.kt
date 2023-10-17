package com.am.bird_watching_app.ui.onBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.am.bird_watching_app.R

class Slide1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slide1)

        val next = findViewById<TextView>(R.id.next1)
        next.setOnClickListener { startActivity(Intent(this, Slide2::class.java))
        finish()}
    }

}
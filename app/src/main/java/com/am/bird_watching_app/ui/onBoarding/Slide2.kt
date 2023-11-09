package com.am.bird_watching_app.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.am.bird_watching_app.R

class Slide2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slide2)

        val next = findViewById<TextView>(R.id.next2)
        val back = findViewById<TextView>(R.id.back1)
        next.setOnClickListener { startActivity(Intent(this, Slide3::class.java))
            finish()}
        back.setOnClickListener { startActivity(Intent(this, Slide1::class.java))
            finish()}
    }
}
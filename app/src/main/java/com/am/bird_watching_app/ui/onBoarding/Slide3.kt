package com.am.bird_watching_app.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.am.bird_watching_app.R
import com.am.bird_watching_app.auth.Register

class Slide3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slide3)

        val next = findViewById<TextView>(R.id.next3)
        val back = findViewById<TextView>(R.id.back2)
        next.setOnClickListener { startActivity(Intent(this, Register::class.java))
            finish()}
        back.setOnClickListener { startActivity(Intent(this, Slide2::class.java))
            finish()}
    }
}
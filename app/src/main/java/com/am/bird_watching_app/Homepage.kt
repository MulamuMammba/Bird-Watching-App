package com.am.bird_watching_app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val userEmailTextView = findViewById<TextView>(R.id.userEmail)
        userEmailTextView.text = Firebase.auth.currentUser?.email ?: "your_email@example.com"
        val gearIcon = findViewById<ImageView>(R.id.gearIcon)
        gearIcon.setOnClickListener {
            val settingsIntent = Intent(this, Settings::class.java)
            startActivity(settingsIntent)
        }

        val menuList = findViewById<ListView>(R.id.menuList)

        val menuItems = arrayOf("Search for a bird", "Load bird list", "Find bird hotspots", "Personal notes")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menuItems)
        menuList.adapter = adapter

        menuList.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    startActivity(Intent(this, BirdList::class.java))
                }
                1 -> {
                    startActivity(Intent(this, BirdList::class.java))
                }
                2 -> {
                     startActivity(Intent(this, Hotspots::class.java))
                }
                3 -> {
                    startActivity(Intent(this, Notes::class.java))
                }

            }
        }
    }
}
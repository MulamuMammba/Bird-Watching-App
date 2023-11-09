package com.am.bird_watching_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.bird_watching_app.R
import com.am.bird_watching_app.adapters.BirdListAdapter

class BirdList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bird_list)

        val recyclerView = findViewById<RecyclerView>(R.id.birds_recyclerview)


        val adapter = BirdListAdapter(this) { birdItem ->
            // Handle item click here
            val intent = Intent(this, BirdDetail::class.java)
            intent.putExtra("selected_bird", birdItem)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }
}

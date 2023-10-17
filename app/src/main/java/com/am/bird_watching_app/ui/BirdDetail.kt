package com.am.bird_watching_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.am.bird_watching_app.R
import com.am.bird_watching_app.model.BirdItem

class BirdDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bird_detail)

        val selectedBird = intent.getSerializableExtra("selected_bird") as BirdItem

        val birdName= findViewById<TextView>(R.id.bird_title)
        val birdDescription = findViewById<TextView>(R.id.description)
        val birdImage = findViewById<ImageView>(R.id.bird_image_detail)

        birdName.text = selectedBird.name
        birdDescription.text = selectedBird.description
        birdImage.setImageDrawable(getDrawable(R.drawable.ic_launcher_background))

    }
}
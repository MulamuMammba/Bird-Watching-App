package com.am.bird_watching_app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.am.bird_watching_app.R
import com.am.bird_watching_app.model.BirdItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class BirdDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bird_detail)

        val selectedBird = intent.getSerializableExtra("selected_bird") as BirdItem

        val birdName= findViewById<TextView>(R.id.bird_title)
        val birdDescription = findViewById<TextView>(R.id.description)
        val birdImage = findViewById<ImageView>(R.id.bird_image_detail)

        birdName.text = selectedBird.name
        birdDescription.text = selectedBird.location
        birdImage.setImageDrawable(getDrawable(R.drawable.ic_android_black_24dp))

        val buttonMap= findViewById<TextView>(R.id.view_on_map)
        buttonMap.setOnClickListener{
            val intent = Intent(this, MapPage::class.java)
            intent.putExtra("selected_bird", selectedBird)
            startActivity(intent)
        }
        if (!selectedBird.pictureUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(selectedBird.pictureUrl)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(29)))
                .into(birdImage)
        } else {
            birdImage.setImageResource(R.drawable.ic_launcher_foreground)
        }


    }
}
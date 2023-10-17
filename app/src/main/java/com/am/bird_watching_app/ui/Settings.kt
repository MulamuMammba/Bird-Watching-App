package com.am.bird_watching_app.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.am.bird_watching_app.R


class Settings : AppCompatActivity() {
    private lateinit var distanceMeasure: String
    private lateinit var maxTravelDistance: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        distanceMeasure = sh.getString("distanceMeasure", "km").toString()
        maxTravelDistance = sh.getString("maxTravelDistance", "small").toString()


        val maxTravel = findViewById<TextView>(R.id.max_travel_distance)
        val distanceUnit = findViewById<TextView>(R.id.distance_unit)

        maxTravel.text = maxTravelDistance
        distanceUnit.text = distanceMeasure

        maxTravel.setOnClickListener {
            lateinit var output:String
            when(maxTravelDistance){
                "small" -> output="medium"
                "medium" -> output="large"
                "large" -> output="largest"
                "largest" -> output="small"
            }

            maxTravelDistance = output
            maxTravel.text = maxTravelDistance
        }

        distanceUnit.setOnClickListener {
            lateinit var output:String
            when(distanceMeasure){
                "km" -> output="miles"
                "miles" -> output="km"
            }

            distanceMeasure = output
            distanceUnit.text = distanceMeasure
        }

    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()
        myEdit.putString("distanceMeasure", distanceMeasure)
        myEdit.putString("maxTravelDistance", maxTravelDistance)
        myEdit.apply()
    }


}
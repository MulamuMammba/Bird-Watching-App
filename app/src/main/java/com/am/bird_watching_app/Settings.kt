package com.am.bird_watching_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Settings : AppCompatActivity() {
    private lateinit var distanceMeasure: String
    private lateinit var maxTravelDistance: String
    private lateinit var userId: String

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        userId = auth.currentUser?.uid ?: ""

        fetchSettingsFromFirebase()

        val maxTravel = findViewById<TextView>(R.id.max_travel_distance)
        val distanceUnit = findViewById<TextView>(R.id.distance_unit)

        maxTravel.text = maxTravelDistance
        distanceUnit.text = distanceMeasure

        maxTravel.setOnClickListener {
            updateMaxTravelSetting()
        }

        distanceUnit.setOnClickListener {
            updateDistanceMeasureSetting()
        }
    }

    private fun updateMaxTravelSetting() {
        lateinit var output: String
        when (maxTravelDistance) {
            "small" -> output = "medium"
            "medium" -> output = "large"
            "large" -> output = "largest"
            "largest" -> output = "small"
        }

        maxTravelDistance = output
        updateSettingsInFirebase()
    }

    private fun updateDistanceMeasureSetting() {
        lateinit var output: String
        when (distanceMeasure) {
            "km" -> output = "miles"
            "miles" -> output = "km"
        }

        distanceMeasure = output
        updateSettingsInFirebase()
    }

    private fun updateSettingsInFirebase() {
        val settingsRef = database.reference.child("users").child(userId).child("settings")
        val settingsMap = HashMap<String, Any>()
        settingsMap["distanceMeasure"] = distanceMeasure
        settingsMap["maxTravelDistance"] = maxTravelDistance

        settingsRef.setValue(settingsMap)
    }

    private fun fetchSettingsFromFirebase() {
        val settingsRef = database.reference.child("users").child(userId).child("settings")
        settingsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    distanceMeasure = snapshot.child("distanceMeasure").value.toString()
                    maxTravelDistance = snapshot.child("maxTravelDistance").value.toString()

                    updateUI()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun updateUI() {
        val maxTravel = findViewById<TextView>(R.id.max_travel_distance)
        val distanceUnit = findViewById<TextView>(R.id.distance_unit)

        maxTravel.text = maxTravelDistance
        distanceUnit.text = distanceMeasure
    }
}

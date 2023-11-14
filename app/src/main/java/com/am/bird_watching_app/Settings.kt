package com.am.bird_watching_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Settings : AppCompatActivity() {
    private var distanceMeasure: String = "default"
    private var maxTravelDistance: String = "default"

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
            "default" -> output = "small"
            "small" -> output = "medium"
            "medium" -> output = "large"
            "large" -> output = "largest"
            "largest" -> output = "small"
        }

        maxTravelDistance = output
        GlobalScope.launch(Dispatchers.IO) {
            updateSettingsInFirebase()
        }
    }

    private fun updateDistanceMeasureSetting() {
        lateinit var output: String
        when (distanceMeasure) {
            "default" -> output = "km"
            "km" -> output = "miles"
            "miles" -> output = "km"
        }

        distanceMeasure = output
        GlobalScope.launch(Dispatchers.IO) {
            updateSettingsInFirebase()
        }
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

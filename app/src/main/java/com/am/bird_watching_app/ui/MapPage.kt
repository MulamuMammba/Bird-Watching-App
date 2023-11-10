package com.am.bird_watching_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.am.bird_watching_app.R
import com.am.bird_watching_app.api.LocationApi
import com.am.bird_watching_app.model.BirdItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapPage : AppCompatActivity() {

    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_page)

        val selectedBird = intent.getSerializableExtra("selected_bird") as BirdItem

        val title = findViewById<TextView>(R.id.mapTitle)
        title.text = selectedBird.name

        val userLocation = LocationApi(this, this).requestLocationPermissionAndFetchLocation()

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)


        mapView.getMapAsync { googleMap ->
            val location = LatLng(selectedBird.latitude.toDouble(), selectedBird.longitude.toDouble())
            val userLatLng = LatLng(userLocation!!.first, userLocation.second)

            googleMap.addMarker(MarkerOptions().position(location).title(selectedBird.name))

            googleMap.addMarker(MarkerOptions().position(userLatLng).title("Your location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
        }


    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
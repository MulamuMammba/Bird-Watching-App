package com.am.bird_watching_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.am.bird_watching_app.R
import com.am.bird_watching_app.api.ApiService
import com.am.bird_watching_app.api.LocationApi
import com.am.bird_watching_app.model.BirdItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Hotspots : AppCompatActivity() {
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotspots)


        val userLocation = LocationApi(this, this).requestLocationPermissionAndFetchLocation()


        mapView = findViewById(R.id.hotspotMapView)
        mapView.onCreate(savedInstanceState)


        mapView.getMapAsync { googleMap ->
            GlobalScope.launch(Dispatchers.IO) {
            }

            googleMap.addMarker(MarkerOptions().position(LatLng(-26.085334,27.978662)).title("Randburg Hotspot"))
            googleMap.addMarker(MarkerOptions().position(LatLng(-26.07143,28.00251)).title("Sandton Hotspot"))
            googleMap.addMarker(MarkerOptions().position(LatLng(-26.115,27.985)).title("Randburg Hotspot"))
            googleMap.addMarker(MarkerOptions().position(LatLng(-26.111,28.253)).title("Kempton Park Hotspot"))

            val userLatLng = LatLng(userLocation!!.first, userLocation.second)
            googleMap.addMarker(MarkerOptions().position(userLatLng).title("Your location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng))

        }


    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
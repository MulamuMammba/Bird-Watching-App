package com.am.bird_watching_app.ui

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
            val hotspotsList = ApiService().fetchHotspots(userLocation!!.first,userLocation.second)
                for (hotspot in hotspotsList){
                    val location = LatLng(hotspot.latitude, hotspot.longitude)
                    googleMap.addMarker(MarkerOptions().position(location).title(hotspot.name))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))}

                }

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
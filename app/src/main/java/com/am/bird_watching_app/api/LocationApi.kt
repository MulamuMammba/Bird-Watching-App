package com.am.bird_watching_app.api

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LocationApi(private val context: Context, private val activity: Activity) {
    private val LOCATION_PERMISSION_REQUEST = 1

    fun requestLocationPermissionAndFetchLocation(): Pair<Double, Double>? {
        return if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
            null
        } else {
            getDeviceLocation()
        }
    }

    private fun getDeviceLocation(): Pair<Double, Double>? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    return Pair(latitude, longitude)
                }
            }
        } else {
            Toast.makeText(context, "The app won't work as intended without location access", Toast.LENGTH_SHORT).show()
        }

        return null
    }
}

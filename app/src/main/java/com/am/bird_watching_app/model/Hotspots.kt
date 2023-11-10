package com.am.bird_watching_app.model

import java.io.Serializable

data class Hotspots(
    val name: String,
    val latitude: Double,
    val longitude: Double
) : Serializable

package com.am.bird_watching_app.model

import java.io.Serializable

data class Hotspots(
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : Serializable

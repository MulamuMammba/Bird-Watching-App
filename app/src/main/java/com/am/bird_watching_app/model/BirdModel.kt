package com.am.bird_watching_app.model

import java.io.Serializable

data class BirdItem(
    val location: String,
    val name: String,
    val pictureUrl: String,
    val description: String,
    val latitude: String,
    val longitude : String
): Serializable
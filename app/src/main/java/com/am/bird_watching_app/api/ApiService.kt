package com.am.bird_watching_app.api

import com.am.bird_watching_app.model.APIKey
import com.am.bird_watching_app.model.BirdItem
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class ApiService {
    companion object {
        private const val BASE_URL = "https://api.ebird.org/v2/data"
        private val api_KEY = APIKey().eBirdAPIkey
    }

    suspend fun fetchBirdData(latitude: Double, longitude: Double): List<BirdItem> {

        val eBirdApiUrl = "$BASE_URL/obs/geo/recent?lat=$latitude&lng=$longitude"

        val birdItems = mutableListOf<BirdItem>()

        try {
                val url = URL(eBirdApiUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.setRequestProperty("X-eBirdApiToken", api_KEY)

                if(connection.responseCode != 404) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val jsonArray = JSONArray(response.toString())

                for (i in 0 until jsonArray.length()) {
                    val jsonBird = jsonArray.getJSONObject(i)
                    val name = jsonBird.getString("comName")
                    val location = jsonBird.getString("locName")
                    val latitude = jsonBird.getString("lat")
                    val longitude = jsonBird.getString("lng")

                    birdItems.add(BirdItem(location, name, "null", "null",latitude,longitude))
                }
                }else{
                    println("HTTP 404 - Resource not found.")
                }

        } catch (e: Exception) {
            e.printStackTrace()
            birdItems.add(BirdItem("location", "name", "null", "null","null","null"))
        }

        return birdItems
    }
}
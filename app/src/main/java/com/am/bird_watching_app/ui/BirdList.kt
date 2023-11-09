package com.am.bird_watching_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.bird_watching_app.R
import com.am.bird_watching_app.adapters.BirdListAdapter
import com.am.bird_watching_app.api.ApiService
import com.am.bird_watching_app.api.LocationApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BirdList : AppCompatActivity() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bird_list)

        val recyclerView = findViewById<RecyclerView>(R.id.birds_recyclerview)

        val adapter = BirdListAdapter(this) { birdItem ->
            val intent = Intent(this, BirdDetail::class.java)
            intent.putExtra("selected_bird", birdItem)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val location =LocationApi(this,this).requestLocationPermissionAndFetchLocation()

        if (location != null) {
            GlobalScope.launch(Dispatchers.IO) {
                    val birdItems = ApiService().fetchBirdData(location.first, location.second)
                    withContext(Dispatchers.Main) {
                        adapter.submitList(birdItems)
                        recyclerView.adapter = adapter
                    }
            }

        }



    }
}
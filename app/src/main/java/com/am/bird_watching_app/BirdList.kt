package com.am.bird_watching_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.bird_watching_app.adapters.BirdListAdapter
import com.am.bird_watching_app.api.ApiService
import com.am.bird_watching_app.api.LocationApi
import com.am.bird_watching_app.model.BirdItem
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.*
import kotlinx.coroutines.*
import java.util.concurrent.CompletableFuture

class BirdList : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var birdRef: DatabaseReference

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

        database = FirebaseDatabase.getInstance()
        birdRef = database.getReference("birds")

        val location = LocationApi(this, this).requestLocationPermissionAndFetchLocation()

        if (location != null) {
            GlobalScope.launch(Dispatchers.IO) {
                val birdItems = if (isFirebaseEmpty()) {
                    ApiService().fetchBirdData(location.first, location.second)
                } else {
                    fetchBirdDataFromFirebase()
                }

                withContext(Dispatchers.Main) {
                    adapter.submitList(birdItems)
                    recyclerView.adapter = adapter
                }
                pushBirdDataToFirebase(birdItems)
            }
        }
    }

    private fun isFirebaseEmpty(): Boolean {
        val snapshot = Tasks.await(birdRef.limitToFirst(1).get())
        return snapshot.childrenCount == 0L
    }

    private suspend fun fetchBirdDataFromFirebase(): List<BirdItem> = withContext(Dispatchers.IO) {
        val completableFuture = CompletableFuture<List<BirdItem>>()

        birdRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val birdItems = mutableListOf<BirdItem>()
                for (snapshot in dataSnapshot.children) {
                    val birdItem = snapshot.getValue(BirdItem::class.java)
                    birdItem?.let {
                        birdItems.add(it)
                    }
                }
                completableFuture.complete(birdItems)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                completableFuture.completeExceptionally(databaseError.toException())
            }
        })

        return@withContext completableFuture.join()
    }

    private fun pushBirdDataToFirebase(birdItems: List<BirdItem>) {
        birdRef.setValue(birdItems)
    }
}

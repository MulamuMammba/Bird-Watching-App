package com.am.bird_watching_app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.am.bird_watching_app.R
import com.am.bird_watching_app.model.BirdItem
import com.am.bird_watching_app.ui.BirdDetail
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class BirdListAdapter(private val context: Context, private val itemClickListener: (BirdItem) -> Unit) : RecyclerView.Adapter<BirdListAdapter.ViewHolder>() {
    private var birdList: List<BirdItem> = listOf(
        BirdItem(
            location = "North America",
            name = "American Robin",
            pictureUrl = "file:///android_asset/bird1.jpg",
            description = "The American Robin is a migratory songbird."
        ),
        BirdItem(
            location = "Africa",
            name = "African Grey Parrot",
            pictureUrl = "file:///android_asset/bird2.png",
            description = "The African Grey Parrot is known for its intelligence."
        ),
        BirdItem(
            location = "South America",
            name = "Scarlet Macaw",
            pictureUrl = "file:///android_asset/bird3.png",
            description = "The Scarlet Macaw is a colorful parrot species."
        ),
        BirdItem(
            location = "Asia",
            name = "Indian Peafowl",
            pictureUrl = "file:///android_asset/bird4.png",
            description = "The Indian Peafowl is famous for its vibrant plumage."
        ),
        BirdItem(
            location = "Australia",
            name = "Kookaburra",
            pictureUrl = "file:///android_asset/bird5.png",
            description = "The Kookaburra is known for its distinctive laughter-like call."
        ),
        BirdItem(
            location = "Europe",
            name = "Common Nightingale",
            pictureUrl = "file:///android_asset/bird6.png",
            description = "The Common Nightingale is a renowned songbird."
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return birdList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val birdItem = birdList[position]
        holder.bind(birdItem)
    }

    fun submitList(birds: List<BirdItem>) {
        birdList = birds
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val birdImage: ImageView = itemView.findViewById(R.id.bird_image)
        private val birdName: TextView = itemView.findViewById(R.id.bird_name)

        fun bind(birdItem: BirdItem) {
            birdName.text = birdItem.name


            if (!birdItem.pictureUrl.isNullOrEmpty()) {
                // Load and display the image using the provided URL
                Glide.with(context)
                    .load(birdItem.pictureUrl)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(29)))
                    .into(birdImage)
            } else {
                // Show a default image if the URL is not available
                birdImage.setImageResource(R.drawable.ic_launcher_foreground)
            }
            itemView.setOnClickListener {
                itemClickListener(birdItem)
            }
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, BirdDetail::class.java)
                intent.putExtra("selected_bird", birdItem)
                context.startActivity(intent)
            }
        }
    }
}

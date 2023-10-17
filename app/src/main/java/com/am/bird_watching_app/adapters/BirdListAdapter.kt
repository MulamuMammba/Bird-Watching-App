package com.am.bird_watching_app.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.am.bird_watching_app.ui.BirdDetail
import com.am.bird_watching_app.R
import com.am.bird_watching_app.model.BirdItem

class BirdListAdapter(private val itemClickListener: (BirdItem) -> Unit) : RecyclerView.Adapter<BirdListAdapter.ViewHolder>() {
    private var birdList: List<BirdItem> = listOf(
        BirdItem(
            location = "North America",
            name = "American Robin",
            pictureUrl = "https://example.com/american_robin.jpg",
            description = "The American Robin is a migratory songbird."
        ),
        BirdItem(
            location = "Africa",
            name = "African Grey Parrot",
            pictureUrl = "https://example.com/african_grey_parrot.jpg",
            description = "The African Grey Parrot is known for its intelligence."
        ),
        BirdItem(
            location = "South America",
            name = "Scarlet Macaw",
            pictureUrl = "https://example.com/scarlet_macaw.jpg",
            description = "The Scarlet Macaw is a colorful parrot species."
        ),
        BirdItem(
            location = "Asia",
            name = "Indian Peafowl",
            pictureUrl = "https://example.com/indian_peafowl.jpg",
            description = "The Indian Peafowl is famous for its vibrant plumage."
        ),
        BirdItem(
            location = "Australia",
            name = "Kookaburra",
            pictureUrl = "https://example.com/kookaburra.jpg",
            description = "The Kookaburra is known for its distinctive laughter-like call."
        ),
        BirdItem(
            location = "Europe",
            name = "Common Nightingale",
            pictureUrl = "https://example.com/nightingale.jpg",
            description = "The Common Nightingale is a renowned songbird."
        ),
        BirdItem(
            location = "Antarctica",
            name = "Emperor Penguin",
            pictureUrl = "https://example.com/emperor_penguin.jpg",
            description = "The Emperor Penguin is the largest penguin species."
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
            //birdImage.setImageURI()
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

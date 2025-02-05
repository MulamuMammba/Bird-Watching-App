package com.am.bird_watching_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.am.bird_watching_app.R
import com.am.bird_watching_app.model.BirdItem
import com.am.bird_watching_app.model.Note
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BirdListAdapter(private val context: Context, private val itemClickListener: (BirdItem) -> Unit) : RecyclerView.Adapter<BirdListAdapter.ViewHolder>() {
    private var birdList: List<BirdItem> = emptyList()

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
        private val birdImage: ImageView = itemView.findViewById(R.id.bird_image)
        private val birdName: TextView = itemView.findViewById(R.id.bird_name)
        private val birdLocation: TextView = itemView.findViewById(R.id.bird_location)

        fun bind(birdItem: BirdItem) {
            birdName.text = birdItem.name
            birdLocation.text = birdItem.location

            if (!birdItem.pictureUrl.isNullOrEmpty()) {
                Glide.with(context)
                    .load(birdItem.pictureUrl)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(29)))
                    .into(birdImage)
            } else {
                birdImage.setImageResource(R.drawable.ic_launcher_foreground)
            }

            itemView.setOnClickListener {
                itemClickListener(birdItem)
            }
        }
    }
}

class NotesAdapter(private val notesList: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contentTextView: TextView = itemView.findViewById(R.id.noteContentTextView)
        private val timestampTextView: TextView = itemView.findViewById(R.id.noteTimestampTextView)

        fun bind(note: Note) {
            contentTextView.text = note.content
            timestampTextView.text = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(
                Date(note.timestamp)
            )
        }
    }
}


package com.am.bird_watching_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.bird_watching_app.adapters.NotesAdapter
import com.am.bird_watching_app.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Notes : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    private val noteEditText: EditText = findViewById(R.id.noteEditText)
    private val saveNoteButton: Button = findViewById(R.id.saveNoteButton)
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notesList: MutableList<Note>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val notesRecyclerView: RecyclerView = findViewById(R.id.notesRecyclerView)
        notesList = mutableListOf()
        notesAdapter = NotesAdapter(notesList)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesRecyclerView.adapter = notesAdapter

        saveNoteButton.setOnClickListener {
            saveNote()
        }

        displayNotes()
    }

    private fun saveNote() {
        val noteContent = noteEditText.text.toString().trim()

        if (noteContent.isNotEmpty()) {
            val userId = auth.currentUser?.uid
            val notesRef = database.reference.child("users").child(userId!!).child("notes")

            val noteId = notesRef.push().key

            val timestamp = System.currentTimeMillis()

            val noteMap = HashMap<String, Any>()
            noteMap["id"] = noteId!!
            noteMap["content"] = noteContent
            noteMap["timestamp"] = timestamp

            notesRef.child(noteId).setValue(noteMap)

            noteEditText.text.clear()
        }
    }

    private fun displayNotes() {
        val userId = auth.currentUser?.uid
        val notesRef = database.reference.child("users").child(userId!!).child("notes")

        notesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notesList.clear()

                for (noteSnapshot in snapshot.children) {
                    val note = noteSnapshot.getValue(Note::class.java)
                    note?.let { notesList.add(it) }
                }

                notesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}

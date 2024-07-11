package com.shubham.sqlitenoteapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubham.sqlitenoteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NoteDatabaseHelper
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = NoteDatabaseHelper(this)
        adapter = NoteAdapter(db.getAllNotes(), this)

        binding.noteRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.noteRecyclerView.adapter = adapter

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        adapter.refreshData(db.getAllNotes())
        updateImageVisibility()
    }

    private fun updateImageVisibility() {
        if (adapter.itemCount == 0) {
            binding.imgNote.visibility = View.VISIBLE
            binding.txtFirst.visibility = View.VISIBLE
        } else {
            binding.imgNote.visibility = View.GONE
            binding.txtFirst.visibility = View.GONE
        }
    }

}
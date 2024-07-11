package com.shubham.sqlitenoteapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private var note : List<Note>, context: Context) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateBtn: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_items, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return note.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = note[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newNotes: List<Note>){
        note = newNotes
        notifyDataSetChanged()
    }

}
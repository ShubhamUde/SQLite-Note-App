package com.shubham.sqlitenoteapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.shubham.sqlitenoteapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NoteDatabaseHelper
    private var noteID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        noteID = intent.getIntExtra("note_id", -1)
        if (noteID == -1) {
            finish()
            return
        }

        val note = db.getNoteByID(noteID)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEtText.setText(note.content)

        binding.updateSaveBtn.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEtText.text.toString()
            val updatedNote = Note(noteID, newTitle, newContent)
            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()

        }

        binding.deleteBtn.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        binding.backBtnUp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showDeleteConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete_confirmation, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.dialogConfirmButton).setOnClickListener {
            db.deleteNote(noteID)
            finish()
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
            dialogBuilder.dismiss()
        }

        dialogView.findViewById<Button>(R.id.dialogCancelButton).setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBuilder.show()
    }
}
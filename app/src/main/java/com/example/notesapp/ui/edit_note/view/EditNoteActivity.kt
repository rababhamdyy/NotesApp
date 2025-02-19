package com.example.notesapp.ui.edit_note.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.notesapp.R
import com.example.notesapp.data.models.Note
import com.example.notesapp.databinding.ActivityEditNoteBinding
import com.example.notesapp.ui.edit_note.viewmodel.EditNoteViewModel
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AlertDialog


class EditNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditNoteBinding
    val viewModel: EditNoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_note)
        val noteTitle = intent.getStringExtra("noteTitle")
        val noteText = intent.getStringExtra("noteText")
        val noteId = intent.getIntExtra("noteId", 0)
        val editTextTitle= binding.etTitle
        val editTextNote = binding.etNote
        val note = Note(noteId, title = noteTitle!!, noteText !!)
        binding.note = note

        fun showDeleteConfirmationDialog(view: View, note: Note, onConfirmDelete: () -> Unit) {
            AlertDialog.Builder(view.context)
                .setTitle("Delete Note")
                .setMessage("Do you want to delete this note?")
                .setPositiveButton("Yes") { _, _ ->
                    onConfirmDelete()
                    Snackbar.make(view, "Note deleted", Snackbar.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        binding.btnDel.setOnClickListener {
            showDeleteConfirmationDialog(it, note) {
                viewModel.deleteNote(note)
                finish()
            }
        }

        binding.btEdit.setOnClickListener {

            note.title = editTextTitle.text.toString()
            note.note = editTextNote.text.toString()
            viewModel.editNote(note)
        }


        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

        viewModel.editNote.observe(this, Observer {
            binding.btEdit.hideKeyboard()
            Snackbar.make(binding.editDel, R.string.note_added, Snackbar.LENGTH_LONG)
                .setAction(R.string.dismiss) {
                    finish()
                }.show()
        })
    }
}
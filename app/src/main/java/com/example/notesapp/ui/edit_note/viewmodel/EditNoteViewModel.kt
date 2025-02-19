package com.example.notesapp.ui.edit_note.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.local.AppDatabase
import com.example.notesapp.data.models.Note
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class EditNoteViewModel(app: Application) : AndroidViewModel(app) {
    private val noteDao = AppDatabase.DatabaseBuilder.getInstance(app.applicationContext).noteDao()
    private val _editNote: MutableLiveData<Unit> = MutableLiveData()
    val editNote: LiveData<Unit> = _editNote

    fun editNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.updateNote(note)
            withContext(Dispatchers.Main) {
                _editNote.postValue(Unit)
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(note)
        }
    }
}
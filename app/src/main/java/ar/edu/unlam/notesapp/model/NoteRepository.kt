package ar.edu.unlam.notesapp.model

import androidx.lifecycle.LiveData
import ar.edu.unlam.notesapp.data.Nota

interface NoteRepository {

    suspend fun addNote(nota: Nota)
    suspend fun readAllData(): LiveData<List<Nota>>
    suspend fun updateNote(nota: Nota)
    suspend fun deleteNote(nota: Nota)
    suspend fun deleteAllNotes()

}
package ar.edu.unlam.notesapp.model

import androidx.lifecycle.LiveData
interface NoteRepository {

    suspend fun addNota(nota: Note)
    suspend fun readAllData(): LiveData<List<Note>>
    suspend fun updateNota(nota: Note)
    suspend fun deleteNota(nota: Note)
    suspend fun deleteAllNotas()

}
package ar.edu.unlam.notesapp.model

interface NoteRepository2 {

    suspend fun save(note: Note)
    suspend fun getAll(): List<Note>
}
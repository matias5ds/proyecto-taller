package ar.edu.unlam.notesapp.data

import androidx.lifecycle.LiveData
import ar.edu.unlam.notesapp.model.NoteRepository

class NotaRepository(private val notaDao: NotaDao):NoteRepository{

    val readAllData: LiveData<List<Nota>> =notaDao.readAllData()


    override suspend fun addNote(nota: Nota) {

        notaDao.addNota(nota)
    }


    override suspend fun readAllData(): LiveData<List<Nota>> {
            return readAllData()
    }

    override suspend fun updateNote(nota: Nota) {

        notaDao.updateNota(nota)
    }

    override suspend fun deleteNote(nota: Nota) {

            notaDao.deleteNota(nota)
    }

    override suspend fun deleteAllNotes(){
         notaDao.deleteAllNotas()
    }

}
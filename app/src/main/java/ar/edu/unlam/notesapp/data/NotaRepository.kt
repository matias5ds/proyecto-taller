package ar.edu.unlam.notesapp.data

import androidx.lifecycle.LiveData

class NotaRepository(private val notaDao: NotaDao){

    val readAllData: LiveData<List<Nota>> =notaDao.readAllData()

    suspend fun addNota(nota: Nota){
       notaDao.addNota(nota)
     }

    suspend fun updateNota(nota: Nota){
        notaDao.updateNota(nota)
    }

     suspend fun deleteNota(nota: Nota){
        notaDao.deleteNota(nota)
    }

     suspend fun deleteAllNotas(){
         notaDao.deleteAllNotas()
    }

}
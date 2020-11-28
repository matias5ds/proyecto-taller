package ar.edu.unlam.notesapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.notesapp.data.Nota
import ar.edu.unlam.notesapp.data.NotaDatabase
import ar.edu.unlam.notesapp.data.NotaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotaViewModel(application: Application): AndroidViewModel(application) {

     val readAllData:LiveData<List<Nota>>
    private val repository:NotaRepository

    init {
        val nota_Dao=NotaDatabase.getDatabase(application).notaDao()
        repository=NotaRepository(nota_Dao)
        readAllData=repository.readAllData
    }

    fun addNota(nota: Nota){
        viewModelScope.launch(Dispatchers.IO){
                repository.addNota(nota)
        }
    }

    fun updateNota(nota: Nota){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNota(nota)
        }
    }

    fun deleteNota(nota: Nota){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNota(nota)
        }
    }

    fun deleteAllNotas(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotas()
        }
    }

}
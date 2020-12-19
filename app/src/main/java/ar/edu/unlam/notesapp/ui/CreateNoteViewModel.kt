package ar.edu.unlam.notesapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.notesapp.model.Note
import ar.edu.unlam.notesapp.model.NoteRepository2
import kotlinx.coroutines.launch

class CreateNoteViewModel(private val noteRepository: NoteRepository2) : ViewModel() {
    val status = MutableLiveData<Status>()
    fun save(note: Note) {
        viewModelScope.launch {
            try {
                noteRepository.save(note)
                status.value = Status.SUCCESS
            } catch (ignored: Exception) {
                status.value = Status.ERROR
            }
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,

    }
}
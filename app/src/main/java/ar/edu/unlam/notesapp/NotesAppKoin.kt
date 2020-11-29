package ar.edu.unlam.notesapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import ar.edu.unlam.notesapp.data.NotaDao
import ar.edu.unlam.notesapp.data.NotaDatabase
import ar.edu.unlam.notesapp.data.NotaRepository
import ar.edu.unlam.notesapp.model.NoteRepository
import ar.edu.unlam.notesapp.ui.NotaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NotesAppKoin: Application() {
    val appModule = module {
        single<NotaDao>{ providerDatabase(get()).notaDao() }
        single<NoteRepository> { NotaRepository(get()) }
        viewModel { NotaViewModel(get()) }

    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@NotesAppKoin)
            modules(appModule)
        }
    }

    private fun providerDatabase(context: Context): NotaDatabase {
        return Room.databaseBuilder(context,NotaDatabase::class.java,"nota_database").build()
    }
}
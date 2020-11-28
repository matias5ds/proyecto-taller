package ar.edu.unlam.notesapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
        version = 1,
        entities = [Nota::class]
)

abstract class NotaDatabase:RoomDatabase() {

    abstract fun notaDao():NotaDao

    companion object {
        @Volatile
        private var INSTANCE: NotaDatabase? = null

        fun getDatabase(context: Context): NotaDatabase {
            val  tempInstance= INSTANCE

            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    NotaDatabase::class.java,
                    "nota_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }

        }

}
package ar.edu.unlam.notesapp.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NotaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNota(nota: Nota)

    @Query("SELECT * FROM nota_table ORDER BY id ASC")
    fun readAllData():LiveData<List<Nota>>

    @Update
    suspend fun updateNota(nota: Nota)

    @Delete
    suspend fun deleteNota(nota: Nota)

    @Query("DELETE FROM nota_table")
    fun deleteAllNotas()
}
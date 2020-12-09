package ar.edu.unlam.notesapp.data

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException


class NotaRepositoryTest {

    lateinit var instance:NotaRepository

    @MockK
    lateinit var noteDao:NotaDao

    @Before
    fun setUp()=MockKAnnotations.init(this,relaxUnitFun = true)

    @Test
    @ExperimentalCoroutinesApi
    fun `testQueVerificaCantidadDeNotasAgregadas`(){

        runBlockingTest {
            instance= NotaRepository(noteDao)

            coEvery { noteDao.getAllNotas() } returns listOf(
            Nota(1,"mi title ","mi contenido 1"),
            Nota(2,"mi title 1","mi contenido 2")
            )

            val result=instance.getAllNotes()

            assertThat(result.size).isEqualTo(2)
        }

    }


    @Test
    @ExperimentalCoroutinesApi
    fun `testQueVerificaElContenidoDeUnaPosicion`(){

        runBlockingTest {
            instance= NotaRepository(noteDao)

            coEvery { noteDao.getAllNotas() } returns listOf(
                Nota(1,"mi title 1","esto es un test"),
                Nota(2,"mi title 2","mi contenido perfecto")
            )

            val result=instance.getAllNotes()

            assertThat(result[0].contenido).isEqualTo("esto es un test")
            assertThat(result[1].contenido).isEqualTo("mi contenido perfecto")
            assertThat(result[0].titulo).isEqualTo("mi title 1")
            assertThat(result[1].titulo).isEqualTo("mi title 2")
            assertThat(result[0].id).isEqualTo(1)
            assertThat(result[1].id).isEqualTo(2)
        }

    }


    @Test
    @ExperimentalCoroutinesApi
    fun `error al insertar`(){
        instance = NotaRepository(noteDao)
        coEvery{ noteDao.addNota(any()) } throws RuntimeException("Exception")
        Assertions.assertThatThrownBy {
            runBlockingTest {
                instance.addNote(Nota(1, "testException", "esto es un test"))
            }
        }.isInstanceOf(RuntimeException::class.java)
                .hasMessage(("exception"))
    }



}



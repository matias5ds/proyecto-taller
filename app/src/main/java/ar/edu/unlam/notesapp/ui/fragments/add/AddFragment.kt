package ar.edu.unlam.notesapp.ui.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ar.edu.unlam.notesapp.R
import ar.edu.unlam.notesapp.data.Nota
import ar.edu.unlam.notesapp.ui.NotaViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private lateinit var mNotaViewModel:NotaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_add, container, false)

        mNotaViewModel=ViewModelProvider(this).get(NotaViewModel::class.java)

        view.add_btr.setOnClickListener{
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val titulo=add_titulo.text.toString()
        val contenido=add_contenido.text.toString()

        if(inputCheck(titulo,contenido)){

            val nota= Nota(0,titulo,contenido)

            mNotaViewModel.addNota(nota)
            Toast.makeText(requireContext(),"Nota Guardada",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Titulo y Contenido son obligatorios",Toast.LENGTH_LONG).show()
        }

    }


    private fun inputCheck(titulo: String,contenido: String):Boolean{
        return !(TextUtils.isEmpty(titulo)&&TextUtils.isEmpty(contenido))
    }


}



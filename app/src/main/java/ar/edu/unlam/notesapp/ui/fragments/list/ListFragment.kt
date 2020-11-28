package ar.edu.unlam.notesapp.ui.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.edu.unlam.notesapp.R
import ar.edu.unlam.notesapp.ui.NotaViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private lateinit var  mNotaViewModel: NotaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_list, container, false)

        //Recycler
        val adapter=ListAdapter()
        val recyclerView=view.recyclerview
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        //NotaViewModel
        mNotaViewModel=ViewModelProvider(this).get(NotaViewModel::class.java)
        mNotaViewModel.readAllData.observe(viewLifecycleOwner, Observer {nota->
            adapter.setData(nota)
        })

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        return view

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_delete){
            deleteAllNotas()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllNotas(){
        val builder= AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Si"){_,_->
            mNotaViewModel.deleteAllNotas()
            Toast.makeText(
                requireContext(),
                "Eliminacion Exitosa de las Notas",
                Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("No"){_,_-> }
        builder.setTitle("Eliminar notas?")
        builder.setMessage("¿Estas seguro de eliminar todas las notas?")
        builder.create().show()
    }

}
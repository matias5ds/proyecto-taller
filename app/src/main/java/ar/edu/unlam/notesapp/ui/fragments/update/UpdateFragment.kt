package ar.edu.unlam.notesapp.ui.fragments.update

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ar.edu.unlam.notesapp.R
import ar.edu.unlam.notesapp.data.Nota
import ar.edu.unlam.notesapp.ui.NotaViewModel
import kotlinx.android.synthetic.main.fragment_add.*

import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private  lateinit var mNotaViewModel: NotaViewModel

    val REQUEST_IMAGE_CAPTURE=777

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_update, container, false)

        mNotaViewModel=ViewModelProvider(this).get(NotaViewModel::class.java)
        view.updateTitulo.setText(args.currentNota.titulo)
        view.updateContenido.setText(args.currentNota.contenido)


        view.update_foto.setOnClickListener{checkPermissions()}
        view.update_btr.setOnClickListener{
            updateItem()
        }
        // menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem(){
        val titulo=updateTitulo.text.toString()
        val contenido=updateContenido.text.toString()

        if(inputCheck(titulo,contenido)){

            val updateNota= Nota(args.currentNota.id,titulo,contenido)

            mNotaViewModel.updateNota(updateNota)
            Toast.makeText(requireContext(),"Nota Actualizada",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }else{
            Toast.makeText(requireContext(),"Llene campos para actualizar",Toast.LENGTH_SHORT).show()
            }
    }

    private fun inputCheck(titulo: String,contenido: String):Boolean{
        return !(TextUtils.isEmpty(titulo)||TextUtils.isEmpty(contenido))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_delete){
            deleteNota()
        }
        return super.onOptionsItemSelected(item)
    }

    private  fun deleteNota(){
        val builder=AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Si"){_,_->
            mNotaViewModel.deleteNota(args.currentNota)
            Toast.makeText(
                   requireContext(),
                  "Eliminacion Exitosa: ${args.currentNota.titulo}",
                   Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_-> }
        builder.setTitle("Eliminar nota ${args.currentNota.titulo}?")
        builder.setMessage("¿Estas seguro de eliminar la nota ${args.currentNota.titulo}?")
        builder.create().show()
    }


    private fun checkPermissions() =
        if(ContextCompat.checkSelfPermission(this.requireContext(),
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            requestCameraPermission()
        }else{
            openCamera()
        }


    private fun openCamera() {
        var i= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i,REQUEST_IMAGE_CAPTURE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_IMAGE_CAPTURE){
            val bmp=data?.extras?.get("data")as Bitmap
            update_imagen.setImageBitmap(bmp)
        }
    }


    private fun requestCameraPermission() =
        if(ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(), Manifest.permission.CAMERA)){
            Toast.makeText(this.requireContext(),"Permisos rechazados", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this.requireActivity(),arrayOf(Manifest.permission.CAMERA),777)
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==REQUEST_IMAGE_CAPTURE){
            if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                openCamera()
            }else{
                Toast.makeText(this.requireContext(),"Permiso rechazado por primera vez", Toast.LENGTH_SHORT).show()
            }
        }
    }



}


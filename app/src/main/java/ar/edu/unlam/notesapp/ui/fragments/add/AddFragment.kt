package ar.edu.unlam.notesapp.ui.fragments.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri

import android.os.Bundle
import android.provider.MediaStore

import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ar.edu.unlam.notesapp.R
import ar.edu.unlam.notesapp.data.Nota
import ar.edu.unlam.notesapp.ui.NotaViewModel
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


@Suppress("DEPRECATION")
class AddFragment : Fragment() {

    private lateinit var mNotaViewModel:NotaViewModel
    val REQUEST_IMAGE_CAPTURE=777



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_add, container, false)

        mNotaViewModel=ViewModelProvider(this).get(NotaViewModel::class.java)

        view.btn_foto.setOnClickListener{checkPermissions()}
        view.add_btr.setOnClickListener{
            insertDataToDatabase()
        }
        return view
    }

    private fun checkPermissions() =
            if(ContextCompat.checkSelfPermission(this.requireContext(),Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            requestCameraPermission()
        }else{
            openCamera()
        }


    private fun openCamera() {
        var i=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i,REQUEST_IMAGE_CAPTURE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_IMAGE_CAPTURE){
            val bmp=data?.extras?.get("data")as Bitmap
            add_imagen.setImageBitmap(bmp)
        }
    }


    private fun requestCameraPermission() =
            if(ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(),Manifest.permission.CAMERA)){
                Toast.makeText(this.requireContext(),"Permisos rechazados", Toast.LENGTH_SHORT).show()
            }else{
                ActivityCompat.requestPermissions(this.requireActivity(),arrayOf(Manifest.permission.CAMERA),777)
            }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==REQUEST_IMAGE_CAPTURE){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera()
            }else{
                Toast.makeText(this.requireContext(),"Permiso rechazado por primera vez", Toast.LENGTH_SHORT).show()
            }
        }
    }



        private fun insertDataToDatabase() {

                val titulo = add_titulo.text.toString()
                val contenido = add_contenido.text.toString()


                if (inputCheck(titulo, contenido)) {

                    val nota = Nota(0, titulo, contenido)

                    mNotaViewModel.addNota(nota)
                    Toast.makeText(requireContext(), "Nota Guardada", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_addFragment_to_listFragment)
                } else {
                    Toast.makeText(requireContext(), "Titulo y Contenido son obligatorios", Toast.LENGTH_LONG).show()
                }

            }



    private fun inputCheck(titulo: String,contenido: String):Boolean{
        return !(TextUtils.isEmpty(titulo)&&TextUtils.isEmpty(contenido))
    }


}



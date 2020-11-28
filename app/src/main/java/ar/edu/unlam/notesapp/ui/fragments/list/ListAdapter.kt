package ar.edu.unlam.notesapp.ui.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unlam.notesapp.R
import ar.edu.unlam.notesapp.data.Nota
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter:RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var notaList= emptyList<Nota>()

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=notaList[position]
        holder.itemView.titulo_txt.text=currentItem.titulo
        holder.itemView.contenido_txt.text=currentItem.contenido

        holder.itemView.rowLayout.setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return notaList.size
    }

    fun setData(nota:List<Nota>){
        this.notaList=nota
        notifyDataSetChanged()
    }

}
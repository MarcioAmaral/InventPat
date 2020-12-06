package br.com.inventpat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.inventpat.R
import br.com.inventpat.databinding.ListItemBinding
import br.com.inventpat.model.inventario.Inventario

class InventRecyclerViewAdapter(private val clickListener: (Inventario)->Unit): RecyclerView.Adapter<InvViewHolder>()
{
    private val inventList = ArrayList<Inventario>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent,false)
        return InvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InvViewHolder, position: Int) {
        holder.bind(inventList[position],clickListener)
    }

    override fun getItemCount() = inventList.size

    fun setList(inventarios: List<Inventario>){
        inventList.clear()
        inventList.addAll(inventarios)
    }
}


class InvViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(inventario: Inventario, clickListener: (Inventario) -> Unit){
        binding.idItem.text = inventario.inventarioId.toString()
        binding.descrItem.text = inventario.descricao
        binding.listItemLayout.setOnClickListener {
            clickListener(inventario)
        }
    }
}
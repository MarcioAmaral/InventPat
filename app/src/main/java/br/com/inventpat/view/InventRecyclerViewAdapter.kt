package br.com.inventpat.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.iterator
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.inventpat.R
import br.com.inventpat.databinding.ListItemBinding
import br.com.inventpat.model.inventario.Inventario
import br.com.inventpat.util.Event

class InventRecyclerViewAdapter(private val clickListener: (Inventario)->Unit): RecyclerView.Adapter<InvViewHolder>()
{
    private val inventList = ArrayList<Inventario>()

    companion object {
        var inventSelect: Inventario? = null
    }

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
        binding.idItem.text = inventario.inventarioId
        binding.descrItem.text = inventario.descricao
        binding.listItemLayout.setOnClickListener {
            InventRecyclerViewAdapter.inventSelect = inventario
            clickListener(inventario)
        }

    }
}
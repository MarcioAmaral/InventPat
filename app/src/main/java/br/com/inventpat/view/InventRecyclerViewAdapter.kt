package br.com.inventpat.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.inventpat.R
import br.com.inventpat.databinding.ListItemBinding
import br.com.inventpat.model.inventario.Inventario
import br.com.inventpat.util.Event

class InventRecyclerViewAdapter(private val clickListener: (Inventario)->Unit): RecyclerView.Adapter<InvViewHolder>()
{
    private val inventList = ArrayList<Inventario>()
    var itemLocal = ""
    var itemUnidade = ""
    var itemEnd1 = ""
    var itemEnd2 = ""
    var itemEnd3 = ""
    var itemEnd4 = ""
    var itemEnd5 = ""
    var itemEnd6 = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent,false)
        return InvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InvViewHolder, position: Int) {
        holder.bind(inventList[position],clickListener)
        itemLocal = inventList[position].local
        itemUnidade = inventList[position].unid_medida
        itemEnd1 = inventList[position].endereco1.toString()
        itemEnd2 = inventList[position].endereco2.toString()
        itemEnd3 = inventList[position].endereco3.toString()
        itemEnd4 = inventList[position].endereco4.toString()
        itemEnd5 = inventList[position].endereco5.toString()
        itemEnd6 = inventList[position].endereco6.toString()
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
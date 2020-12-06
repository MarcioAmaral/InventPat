package br.com.inventpat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.inventpat.model.inventario.InventarioRepository
import br.com.invpatrim.viewmodel.InventarioViewModel
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

class InventarioViewModelFactory(private val repository: InventarioRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InventarioViewModel::class.java)) {
            return InventarioViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
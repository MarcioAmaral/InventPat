package br.com.inventpat.model.inventario

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InventarioRepository(private val dao: InventarioDao) {

    fun insert(inventario: Inventario): Long {
        return dao.insert(inventario)
    }

    fun update(inventario: Inventario): Int {
        return dao.update(inventario)
    }

    fun delete(inventario: Inventario): Int {
        return dao.delete(inventario)
    }

    fun clear(): Int {
        return dao.clearInventario()
    }

    suspend fun getInventario(key: String): Inventario{
        return dao.getInventario(key)
    }

    //val inventarios = dao.getAllInventarios()
    fun getAllInventario(): LiveData<List<Inventario>> {
        return dao.getAllInventarios()
    }

    suspend fun countInventario() = withContext(Dispatchers.Default) {
        dao.countInventario()
    }

}
package br.com.inventpat.model.inventario

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InventarioRepository(private val dao: InventarioDao) {

    val inventarios = dao.getAllInventarios()

    suspend fun insert(inventario: Inventario): Long {
        return dao.insert(inventario)
    }

    suspend fun update(inventario: Inventario): Int {
        return dao.update(inventario)
    }

    suspend fun delete(inventario: Inventario): Int {
        return dao.delete(inventario)
    }

    suspend fun clear(): Int {
        return dao.clearInventario()
    }

    suspend fun getInventario(){
        dao.getAllInventarios()
    }

    suspend fun getAllInventario(key: String){
        dao.getInventario(key)
    }

    suspend fun countInventario() = withContext(Dispatchers.Default) {
        dao.countInventario()
    }

}
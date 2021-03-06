package br.com.inventpat.model.inventario

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InventarioDao {
    @Insert
    fun insert(inventario: Inventario): Long

    @Update
    fun update(inventario: Inventario): Int

    @Delete
    fun delete(inventario: Inventario): Int

    @Query("DELETE FROM inventario WHERE inventarioId = :key")
    fun deleteKey(key: String)

    @Query("DELETE FROM inventario")
    fun clearInventario(): Int

    @Query("SELECT * FROM inventario WHERE inventarioId = :key")
    suspend fun getInventario(key: String): Inventario

    @Query("SELECT * FROM inventario ORDER BY inventarioId DESC")
    fun getAllInventarios(): LiveData<List<Inventario>>

    @Query("SELECT COUNT(*) FROM inventario")
    fun countInventario(): Cursor

}
package br.com.inventpat.model.inventario

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.*

@Database(entities = [Inventario::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class InventarioDatabase : RoomDatabase() {

    abstract val inventarioDao: InventarioDao

    companion object {
        @Volatile
        private var INSTANCE: InventarioDatabase? = null

        fun getInstance(context: Context): InventarioDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        InventarioDatabase::class.java,
                        "invent_pat_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
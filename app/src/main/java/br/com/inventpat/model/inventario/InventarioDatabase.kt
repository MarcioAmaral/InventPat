package br.com.inventpat.model.inventario

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Inventario::class], version = 2, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class InventarioDatabase : RoomDatabase() {

    abstract val inventarioDao: InventarioDao

    companion object {
        @Volatile
        private var INSTANCE: InventarioDatabase? = null
        const val BASE_NOME = "invent_pat_database"

        fun getInstance(context: Context): InventarioDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        InventarioDatabase::class.java,
                        BASE_NOME
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
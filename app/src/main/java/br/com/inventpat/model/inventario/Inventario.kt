package br.com.inventpat.model.inventario

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.inventpat.util.Converters

@Entity
data class Inventario(
    @PrimaryKey val inventarioId: Int,
    val empresa: Int,
    val unid_negocio: String,
    val local: String,
    val descricao: String,
    val unid_medida: String,
    val endereco1: String,
    val endereco2: String,
    val endereco3: String,
    val endereco4: String,
    val endereco5: String,
    val endereco6: String,
    /*  @TypeConverters(Converters::class)
      val fabricacao: Long = 0L,
      @TypeConverters(Converters::class)
      val validade: Long = 0L,
      @TypeConverters(Converters::class)
      val contagem1: Long = 0L,
      @TypeConverters(Converters::class)
      val contagem2: Long = 0L,
      @TypeConverters(Converters::class)
      val contagem3: Long = 0L, */
    val nt_fiscal: String,
    val valor: Double,
    val tecnico: String,
    val foto: String
)
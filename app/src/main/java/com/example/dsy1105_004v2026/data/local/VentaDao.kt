package com.example.dsy1105_004v2026.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas ORDER BY id DESC")
    fun obtenerTodasLasVentas(): Flow<List<VentaEntity>>

    @Query("SELECT COUNT(*) FROM ventas WHERE codigoAutorizacion = :codigo")
    suspend fun existeCodigoAutorizacion(codigo: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVenta(venta: VentaEntity)
}
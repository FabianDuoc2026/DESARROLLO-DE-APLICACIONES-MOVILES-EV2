package com.example.dsy1105_004v2026.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas ORDER BY id DESC")
    fun obtenerTodasLasVentas(): Flow<List<VentaEntity>>

    @Query("SELECT * FROM ventas WHERE id = :id LIMIT 1")
    suspend fun obtenerVentaPorId(id: Int): VentaEntity?

    @Query("SELECT COUNT(*) FROM ventas WHERE codigoAutorizacion = :codigo AND id != :id")
    suspend fun existeCodigoAutorizacion(codigo: String, id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVenta(venta: VentaEntity)

    @Update
    suspend fun actualizarVenta(venta: VentaEntity)
}
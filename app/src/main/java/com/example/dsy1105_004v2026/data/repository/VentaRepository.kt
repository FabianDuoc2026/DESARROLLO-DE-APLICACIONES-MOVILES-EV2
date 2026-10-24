package com.example.dsy1105_004v2026.data.repository

import com.example.dsy1105_004v2026.data.local.VentaDao
import com.example.dsy1105_004v2026.data.local.VentaEntity
import kotlinx.coroutines.flow.Flow

class VentaRepository(private val ventaDao: VentaDao) {
    val todasLasVentas: Flow<List<VentaEntity>> = ventaDao.obtenerTodasLasVentas()

    suspend fun existeCodigo(codigo: String): Boolean = ventaDao.existeCodigoAutorizacion(codigo) > 0

    suspend fun guardarVenta(venta: VentaEntity) {
        ventaDao.insertarVenta(venta)
    }
}
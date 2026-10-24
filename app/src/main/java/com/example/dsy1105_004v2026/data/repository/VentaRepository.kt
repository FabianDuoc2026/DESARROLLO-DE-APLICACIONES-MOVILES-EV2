package com.example.dsy1105_004v2026.data.repository

import com.example.dsy1105_004v2026.data.local.VentaDao
import com.example.dsy1105_004v2026.data.local.VentaEntity
import kotlinx.coroutines.flow.Flow

class VentaRepository(private val ventaDao: VentaDao) {
    val todasLasVentas: Flow<List<VentaEntity>> = ventaDao.obtenerTodasLasVentas()

    suspend fun obtenerVentaPorId(id: Int): VentaEntity? = ventaDao.obtenerVentaPorId(id)

    suspend fun existeCodigo(codigo: String, id: Int): Boolean =
        ventaDao.existeCodigoAutorizacion(codigo, id) > 0

    suspend fun guardarVenta(venta: VentaEntity) {
        if (venta.id == 0) {
            ventaDao.insertarVenta(venta)
        } else {
            ventaDao.actualizarVenta(venta)
        }
    }
}
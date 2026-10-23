package com.example.dsy1105_004v2026.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsy1105_004v2026.data.local.database.AppDatabase
import com.example.dsy1105_004v2026.data.local.entities.VentaEntity
import com.example.dsy1105_004v2026.repository.VentaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class VendedorViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: VentaRepository
    init {
        val dao = AppDatabase.getDatabase(application).ventaDao()
        repository = VentaRepository(dao)
    }

    var clienteNombre = MutableStateFlow("")
    var errorCliente = MutableStateFlow<String?>(null)

    var rutCliente = MutableStateFlow("")
    var errorRut = MutableStateFlow<String?>(null)

    var tipoConvenio = MutableStateFlow("Clínica Dental")
    var prestador = MutableStateFlow("Centro Dental Ficticio")

    var montoVenta = MutableStateFlow("")
    var errorMonto = MutableStateFlow<String?>(null)

    var codigoAuth = MutableStateFlow("")
    var errorCodigo = MutableStateFlow<String?>(null)

    var uriRespaldoFoto = MutableStateFlow<String?>(null)

    fun validarYGuardar(vendedorId: String, comoBorrador: Boolean, onResultado: (Boolean, String) -> Unit) {
        val nombreVal = clienteNombre.value.isNotBlank()
        val rutVal = rutCliente.value.isNotBlank()
        val montoNum = montoVenta.value.toDoubleOrNull() ?: 0.0
        val codigoVal = codigoAuth.value.length >= 3
        val fotoVal = uriRespaldoFoto.value != null

        errorCliente.value = if (!nombreVal) "Nombre de cliente obligatorio" else null
        errorRut.value = if (!rutVal) "RUT ficticio obligatorio" else null
        errorMonto.value = if (montoNum <= 0) "Monto válido requerido" else null
        errorCodigo.value = if (!codigoVal) "Código mínimo de 3 caracteres" else null

        if (!nombreVal || !rutVal || montoNum <= 0 || !codigoVal || (!comoBorrador && !fotoVal)) {
            onResultado(false, "Corrija los errores del formulario")
            return
        }

        viewModelScope.launch {
            if (!comoBorrador && repository.codigoExiste(codigoAuth.value)) {
                errorCodigo.value = "Este código de autorización ya fue registrado"
                onResultado(false, "Código duplicado detectado")
                return@launch
            }

            val estadoFinal = if (comoBorrador) "BORRADOR" else "PENDIENTE_REVISION"
            val venta = VentaEntity(
                vendedorId = vendedorId,
                clienteNombre = clienteNombre.value,
                clienteRut = rutCliente.value,
                tipoConvenio = tipoConvenio.value,
                prestadorFicticio = prestador.value,
                monto = montoNum,
                codigoAutorizacion = codigoAuth.value,
                uriRespaldo = uriRespaldoFoto.value ?: "",
                estado = estadoFinal
            )
            repository.guardar(venta)
            onResultado(true, if (comoBorrador) "Borrador guardado con éxito" else "Venta enviada a revisión")
        }
    }

    fun obtenerMisVentas(vendedorId: String) = repository.obtenerVentasVendedor(vendedorId)
}
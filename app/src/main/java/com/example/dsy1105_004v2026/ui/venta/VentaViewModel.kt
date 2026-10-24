package com.example.dsy1105_004v2026.ui.venta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsy1105_004v2026.data.local.VentaEntity
import com.example.dsy1105_004v2026.data.repository.VentaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VentaViewModel(private val repository: VentaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(VentaUiState())
    val uiState: StateFlow<VentaUiState> = _uiState.asStateFlow()

    val listaVentas = repository.todasLasVentas

    fun cargarVentaParaEdicion(id: Int) {
        viewModelScope.launch {
            val venta = repository.obtenerVentaPorId(id)
            if (venta != null) {
                _uiState.update {
                    it.copy(
                        id = venta.id,
                        tipoServicio = venta.tipoServicio,
                        fechaOperacion = venta.fechaOperacion,
                        cliente = venta.cliente,
                        monto = venta.monto.toString(),
                        codigoAutorizacion = venta.codigoAutorizacion,
                        respaldoUri = venta.respaldoUri,
                        estado = venta.estado,
                        observaciones = venta.observaciones
                    )
                }
            }
        }
    }

    fun onTipoServicioChange(valor: String) = _uiState.update { it.copy(tipoServicio = valor, tipoServicioError = null) }
    fun onFechaChange(valor: String) = _uiState.update { it.copy(fechaOperacion = valor, fechaError = null) }
    fun onClienteChange(valor: String) = _uiState.update { it.copy(cliente = valor, clienteError = null) }
    fun onMontoChange(valor: String) = _uiState.update { it.copy(monto = valor, montoError = null) }
    fun onCodigoChange(valor: String) = _uiState.update { it.copy(codigoAutorizacion = valor, codigoError = null) }
    fun onRespaldoCaptured(uri: String) = _uiState.update { it.copy(respaldoUri = uri) }
    fun onObservacionesChange(valor: String) = _uiState.update { it.copy(observaciones = valor) }
    fun onRespaldoChange(uri: String) {
        _uiState.value = _uiState.value.copy(respaldoUri = uri)
    }

    fun guardarVenta() {
        viewModelScope.launch {
            val state = _uiState.value

            val tipoErr = if (state.tipoServicio.isBlank()) "El tipo de servicio es obligatorio" else null
            val fechaErr = if (state.fechaOperacion.isBlank()) "La fecha es obligatoria" else null
            val clienteErr = if (state.cliente.isBlank()) "El cliente es obligatorio" else null

            val montoDouble = state.monto.toDoubleOrNull()
            val montoErr = when {
                state.monto.isBlank() -> "El monto es obligatorio"
                montoDouble == null || montoDouble.isNaN() || montoDouble.isInfinite() || montoDouble <= 0 ->
                    "Debe ser un número mayor a 0"
                else -> null
            }

            val codigo = state.codigoAutorizacion.trim()
            val codigoExiste = codigo.isNotBlank() && repository.existeCodigo(codigo, state.id)
            val codigoErr = when {
                codigo.isBlank() -> "El código de autorización es obligatorio"
                codigoExiste -> "El código de autorización ya existe"
                else -> null
            }

            if (tipoErr != null || fechaErr != null || clienteErr != null || montoErr != null || codigoErr != null) {
                _uiState.update {
                    it.copy(
                        tipoServicioError = tipoErr,
                        fechaError = fechaErr,
                        clienteError = clienteErr,
                        montoError = montoErr,
                        codigoError = codigoErr
                    )
                }
                return@launch
            }

            _uiState.update { it.copy(guardando = true) }
            val entidad = VentaEntity(
                id = state.id,
                tipoServicio = state.tipoServicio,
                fechaOperacion = state.fechaOperacion,
                cliente = state.cliente,
                monto = montoDouble!!,
                codigoAutorizacion = codigo,
                respaldoUri = state.respaldoUri,
                estado = state.estado,
                observaciones = state.observaciones
            )

            repository.guardarVenta(entidad)
            _uiState.update { it.copy(guardando = false, guardadoExitosamente = true) }
        }
    }

    fun resetForm() {
        _uiState.value = VentaUiState()
    }
}
package com.example.dsy1105_004v2026.ui.venta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dsy1105_004v2026.data.repository.VentaRepository

class VentaViewModelFactory(private val repository: VentaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VentaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VentaViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}
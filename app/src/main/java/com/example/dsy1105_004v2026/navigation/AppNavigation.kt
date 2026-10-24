package com.example.dsy1105_004v2026.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dsy1105_004v2026.data.local.AppDatabase
import com.example.dsy1105_004v2026.data.repository.VentaRepository
import com.example.dsy1105_004v2026.ui.venta.ListaVentasScreen
import com.example.dsy1105_004v2026.ui.venta.VentaFormScreen
import com.example.dsy1105_004v2026.ui.venta.VentaViewModel
import com.example.dsy1105_004v2026.ui.venta.VentaViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val database = AppDatabase.getDatabase(context)
    val repository = VentaRepository(database.ventaDao())
    val factory = VentaViewModelFactory(repository)
    val ventaViewModel: VentaViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = "lista_ventas"
    ) {
        composable("lista_ventas") {
            ListaVentasScreen(
                viewModel = ventaViewModel,
                onNavegarAFormulario = { id ->
                    navController.navigate("venta_form")
                }
            )
        }
        composable("venta_form") {
            VentaFormScreen(
                viewModel = ventaViewModel,
                onGuardadoExitoso = { navController.popBackStack() }
            )
        }
    }
}
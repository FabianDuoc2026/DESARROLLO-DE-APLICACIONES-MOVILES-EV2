package com.example.dsy1105_004v2026.navigation

import androidx.compose.runtime.Composable
<<<<<<< HEAD
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dsy1105_004v2026.ui.venta.ListaVentasScreen
import com.example.dsy1105_004v2026.ui.venta.VentaFormScreen
import com.example.dsy1105_004v2026.viewmodel.VentaViewModel
=======
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
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
<<<<<<< HEAD
    val ventaViewModel: VentaViewModel = viewModel()

    NavHost(navController = navController, startDestination = "lista_ventas") {
        composable("lista_ventas") {
            ListaVentasScreen(
                viewModel = ventaViewModel,
                onNavegarFormulario = { id ->
                    navController.navigate("venta_form/$id")
                }
            )
        }
        composable(
            route = "venta_form/{ventaId}",
            arguments = listOf(navArgument("ventaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val ventaId = backStackEntry.arguments?.getInt("ventaId") ?: 0
            VentaFormScreen(
                viewModel = ventaViewModel,
                ventaId = ventaId,
                onVolver = { navController.popBackStack() }
=======
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
                onNavegarAFormulario = { navController.navigate("venta_form") }
            )
        }
        composable("venta_form") {
            VentaFormScreen(
                viewModel = ventaViewModel,
                onGuardadoExitoso = { navController.popBackStack() }
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
            )
        }
    }
}
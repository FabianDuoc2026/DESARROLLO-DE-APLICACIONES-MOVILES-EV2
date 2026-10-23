package com.example.dsy1105_004v2026.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dsy1105_004v2026.ui.venta.ListaVentasScreen
import com.example.dsy1105_004v2026.ui.venta.VentaFormScreen
import com.example.dsy1105_004v2026.viewmodel.VentaViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
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
            )
        }
    }
}
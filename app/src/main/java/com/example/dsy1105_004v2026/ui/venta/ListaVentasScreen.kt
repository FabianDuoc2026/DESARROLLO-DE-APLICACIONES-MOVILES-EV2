package com.example.dsy1105_004v2026.ui.venta

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dsy1105_004v2026.data.local.VentaEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaVentasScreen(
    viewModel: VentaViewModel,
    onNavegarAFormulario: (Int?) -> Unit
) {
    val ventas by viewModel.listaVentas.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Histórico de Ventas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.resetForm()
                onNavegarAFormulario(null)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Nueva Venta")
            }
        }
    ) { innerPadding ->
        if (ventas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay ventas registradas localmente.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ventas, key = { it.id }) { venta ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + expandVertically()
                    ) {
                        ItemVentaCard(
                            venta = venta,
                            onClick = {
                                viewModel.cargarVentaParaEdicion(venta.id)
                                onNavegarAFormulario(venta.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemVentaCard(
    venta: VentaEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = venta.tipoServicio, style = MaterialTheme.typography.titleMedium)
                AssistChip(onClick = {}, label = { Text(venta.estado) })
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Cliente: ${venta.cliente}")
            Text(text = "Código Autorización: ${venta.codigoAutorizacion}")
            Text(text = "Fecha: ${venta.fechaOperacion}")
            Text(
                text = "Monto: $${venta.monto}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
package com.example.dsy1105_004v2026.ui.venta

import androidx.compose.animation.*
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
import com.example.dsy1105_004v2026.viewmodel.VentaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaVentasScreen(
    viewModel: VentaViewModel,
    onNavegarFormulario: (Int) -> Unit
) {
    val ventas by viewModel.listaVentas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Ventas - Duoc UC") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavegarFormulario(0) }) {
                Icon(Icons.Default.Add, contentDescription = "Registrar Venta")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (ventas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay ventas registradas localmente.", style = MaterialTheme.typography.bodyLarge)
                }
            } else {

                AnimatedVisibility(
                    visible = ventas.isNotEmpty(),
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(ventas) { venta ->
                            Card(
                                onClick = {

                                    if (venta.estado != "Validada") {
                                        onNavegarFormulario(venta.id)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Código: ${venta.codigoAutorizacion}", style = MaterialTheme.typography.titleMedium)
                                        Badge(containerColor = MaterialTheme.colorScheme.secondary) {
                                            Text(text = venta.estado, modifier = Modifier.padding(4.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Servicio: ${venta.tipoServicio}")
                                    Text(text = "Cliente: ${venta.cliente}")
                                    Text(text = "Monto: $${venta.monto}", style = MaterialTheme.typography.bodyMedium)
                                    Text(text = "Fecha: ${venta.fechaOperacion}", style = MaterialTheme.typography.bodySmall)

                                    if (venta.estado == "Validada") {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "🔒 Venta Validada (No modificable)", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.example.dsy1105_004v2026.ui.screens.vendedor

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dsy1105_004v2026.data.model.Usuario
import com.example.dsy1105_004v2026.viewmodel.VendedorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendedorScreen(usuario: Usuario, viewModel: VendedorViewModel, onCerrarSesion: () -> Unit) {
    var pestanaActual by remember { mutableStateOf(0) }
    var mensajeSnack by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Recurso Nativo: Selector de Galería del dispositivo para adjuntar comprobante ficticio
    val galeriaLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.uriRespaldoFoto.value = it.toString() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel Vendedor: ${usuario.nombre}") },
                actions = { IconButton(onClick = onCerrarSesion) { Icon(Icons.Default.ExitToApp, "Salir") } }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.AddCircle, null) },
                    label = { Text("Registrar Venta") },
                    selected = pestanaActual == 0,
                    onClick = { pestanaActual = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, null) },
                    label = { Text("Mis Ventas") },
                    selected = pestanaActual == 1,
                    onClick = { pestanaActual = 1 }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (pestanaActual == 0) {
                Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Nueva Venta (Datos Ficticios)", style = MaterialTheme.typography.titleMedium)

                    OutlinedTextField(
                        value = viewModel.clienteNombre.collectAsState().value,
                        onValueChange = { viewModel.clienteNombre.value = it },
                        label = { Text("Nombre Ficticio del Cliente *") },
                        leadingIcon = { Icon(Icons.Default.Person, null) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.rutCliente.collectAsState().value,
                        onValueChange = { viewModel.rutCliente.value = it },
                        label = { Text("RUT Ficticio (ej: 11.111.111-K) *") },
                        leadingIcon = { Icon(Icons.Default.Badge, null) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.montoVenta.collectAsState().value,
                        onValueChange = { viewModel.montoVenta.value = it },
                        label = { Text("Monto Ficticio ($) *") },
                        leadingIcon = { Icon(Icons.Default.AttachMoney, null) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.codigoAuth.collectAsState().value,
                        onValueChange = { viewModel.codigoAuth.value = it },
                        label = { Text("Código de Autorización Ficticio *") },
                        leadingIcon = { Icon(Icons.Default.Key, null) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Botón de Recurso Nativo (Galería)
                    OutlinedButton(
                        onClick = { galeriaLauncher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.PhotoLibrary, null)
                        Spacer(Modifier.width(8.dp))
                        Text(if (viewModel.uriRespaldoFoto.collectAsState().value == null) "Adjuntar Respaldo (Recurso Nativo) *" else "Respaldo Seleccionado ✓")
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = {
                                viewModel.validarYGuardar(usuario.id, comoBorrador = true) { _, msg ->
                                    mensajeSnack = msg
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) { Text("Guardar Borrador") }

                        Button(
                            onClick = {
                                viewModel.validarYGuardar(usuario.id, comoBorrador = false) { _, msg ->
                                    mensajeSnack = msg
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) { Text("Enviar a Revisión") }
                    }
                }
            } else {
                // Historial de Mis Ventas
                val misVentas by viewModel.obtenerMisVentas(usuario.id).collectAsState(initial = emptyList())
                LazyColumn(modifier = Modifier.padding(16.dp).fillMaxSize()) {
                    items(misVentas) { venta ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Cliente: ${venta.clienteNombre}", style = MaterialTheme.typography.titleSmall)
                                Text("Convenio: ${venta.tipoConvenio} - Monto: $${venta.monto}")
                                Text("Código: ${venta.codigoAutorizacion}", color = MaterialTheme.colorScheme.secondary)
                                Text("Estado: [ ${venta.estado} ]", color = MaterialTheme.colorScheme.primary)
                                if (venta.observacionEncargado.isNotBlank()) {
                                    Text("Observación: ${venta.observacionEncargado}", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    mensajeSnack?.let { msg ->
        LaunchedEffect(msg) {
            snackbarHostState.showSnackbar(msg)
            mensajeSnack = null
        }
    }
}
package com.example.dsy1105_004v2026.ui.venta

import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaFormScreen(
    viewModel: VentaViewModel,
    onGuardadoExitoso: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            viewModel.onRespaldoChange("respaldo_capturado_${System.currentTimeMillis()}.jpg")
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        }
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            viewModel.onFechaChange("$dayOfMonth/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    LaunchedEffect(state.guardadoExitosamente) {
        if (state.guardadoExitosamente) {
            viewModel.resetForm()
            onGuardadoExitoso()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.id == 0) "Registro de Venta" else "Editar Venta #${state.id}") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.tipoServicio,
                onValueChange = viewModel::onTipoServicioChange,
                label = { Text("Tipo de Servicio / Convenio") },
                leadingIcon = { Icon(Icons.Default.Category, contentDescription = null) },
                isError = state.tipoServicioError != null,
                supportingText = { state.tipoServicioError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = state.fechaOperacion,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Fecha de Operación") },
                    leadingIcon = { Icon(Icons.Default.Event, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Seleccionar Fecha")
                        }
                    },
                    isError = state.fechaError != null,
                    supportingText = { state.fechaError?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { datePickerDialog.show() }
                )
            }

            OutlinedTextField(
                value = state.cliente,
                onValueChange = viewModel::onClienteChange,
                label = { Text("Cliente (Ficticio)") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                isError = state.clienteError != null,
                supportingText = { state.clienteError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.monto,
                onValueChange = viewModel::onMontoChange,
                label = { Text("Monto ($)") },
                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                isError = state.montoError != null,
                supportingText = { state.montoError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.codigoAutorizacion,
                onValueChange = viewModel::onCodigoChange,
                label = { Text("Código de Autorización") },
                leadingIcon = { Icon(Icons.Default.Key, contentDescription = null) },
                isError = state.codigoError != null,
                supportingText = { state.codigoError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.observaciones,
                onValueChange = viewModel::onObservacionesChange,
                label = { Text("Observaciones") },
                leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedButton(
                onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Cámara")
                Spacer(Modifier.width(8.dp))
                Text("Capturar Respaldo Fotográfico")
            }

            AnimatedVisibility(visible = state.respaldoUri != null) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Respaldo listo.")
                    }
                }
            }

            Button(
                onClick = { viewModel.guardarVenta() },
                enabled = !state.guardando,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.guardando) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text(if (state.id == 0) "Guardar Registro" else "Actualizar Registro")
                }
            }
        }
    }
}
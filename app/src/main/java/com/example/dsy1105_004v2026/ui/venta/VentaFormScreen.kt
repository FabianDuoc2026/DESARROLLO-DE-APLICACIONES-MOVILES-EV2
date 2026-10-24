package com.example.dsy1105_004v2026.ui.venta

<<<<<<< HEAD
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
=======
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
<<<<<<< HEAD
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dsy1105_004v2026.viewmodel.VentaViewModel
import java.io.File
=======
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaFormScreen(
    viewModel: VentaViewModel,
<<<<<<< HEAD
    ventaId: Int,
    onVolver: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()


    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            tempImageUri?.let { viewModel.onRespaldoChanged(it.toString()) }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            val uri = crearUriTemporal(context)
            tempImageUri = uri
            cameraLauncher.launch(uri)
        }
    }

    LaunchedEffect(ventaId) {
        viewModel.cargarVentaParaEditar(ventaId)
    }

    LaunchedEffect(state.guardadoExitosamente) {
        if (state.guardadoExitosamente) {
            viewModel.limpiarFormulario()
            onVolver()
=======
    onGuardadoExitoso: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.guardadoExitosamente) {
        if (state.guardadoExitosamente) {
            viewModel.resetForm()
            onGuardadoExitoso()
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
        }
    }

    Scaffold(
<<<<<<< HEAD
        topBar = {
            TopAppBar(
                title = { Text(if (ventaId == 0) "Registrar Nueva Venta" else "Modificar Venta") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
=======
        topBar = { TopAppBar(title = { Text("Registro de Venta") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.tipoServicio,
<<<<<<< HEAD
                onValueChange = { viewModel.onTipoServicioChanged(it) },
                label = { Text("Tipo de Convenio o Servicio *") },
                isError = state.tipoServicioError != null,
                supportingText = {
                    AnimatedVisibility(visible = state.tipoServicioError != null) {
                        Text(text = state.tipoServicioError ?: "", color = MaterialTheme.colorScheme.error)
                    }
                },
=======
                onValueChange = viewModel::onTipoServicioChange,
                label = { Text("Tipo de Servicio / Convenio") },
                isError = state.tipoServicioError != null,
                supportingText = { state.tipoServicioError?.let { Text(it) } },
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.fechaOperacion,
<<<<<<< HEAD
                onValueChange = { viewModel.onFechaChanged(it) },
                label = { Text("Fecha de Operación (ej: 25-10-2026) *") },
                isError = state.fechaError != null,
                supportingText = {
                    AnimatedVisibility(visible = state.fechaError != null) {
                        Text(text = state.fechaError ?: "", color = MaterialTheme.colorScheme.error)
                    }
                },
=======
                onValueChange = viewModel::onFechaChange,
                label = { Text("Fecha de Operación (DD/MM/AAAA)") },
                isError = state.fechaError != null,
                supportingText = { state.fechaError?.let { Text(it) } },
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.cliente,
<<<<<<< HEAD
                onValueChange = { viewModel.onClienteChanged(it) },
                label = { Text("Cliente Ficticio *") },
                isError = state.clienteError != null,
                supportingText = {
                    AnimatedVisibility(visible = state.clienteError != null) {
                        Text(text = state.clienteError ?: "", color = MaterialTheme.colorScheme.error)
                    }
                },
=======
                onValueChange = viewModel::onClienteChange,
                label = { Text("Cliente (Ficticio)") },
                isError = state.clienteError != null,
                supportingText = { state.clienteError?.let { Text(it) } },
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.monto,
<<<<<<< HEAD
                onValueChange = { viewModel.onMontoChanged(it) },
                label = { Text("Monto Ficticio ($) *") },
                isError = state.montoError != null,
                supportingText = {
                    AnimatedVisibility(visible = state.montoError != null) {
                        Text(text = state.montoError ?: "", color = MaterialTheme.colorScheme.error)
                    }
                },
=======
                onValueChange = viewModel::onMontoChange,
                label = { Text("Monto ($)") },
                isError = state.montoError != null,
                supportingText = { state.montoError?.let { Text(it) } },
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.codigoAutorizacion,
<<<<<<< HEAD
                onValueChange = { viewModel.onCodigoChanged(it) },
                label = { Text("Código de Autorización Ficticio *") },
                isError = state.codigoError != null,
                supportingText = {
                    AnimatedVisibility(visible = state.codigoError != null) {
                        Text(text = state.codigoError ?: "", color = MaterialTheme.colorScheme.error)
                    }
                },
=======
                onValueChange = viewModel::onCodigoChange,
                label = { Text("Código de Autorización") },
                isError = state.codigoError != null,
                supportingText = { state.codigoError?.let { Text(it) } },
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.observaciones,
<<<<<<< HEAD
                onValueChange = { viewModel.onObservacionesChanged(it) },
                label = { Text("Observaciones (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )


            OutlinedButton(
                onClick = {
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (state.respaldoUri == null) "Capturar Respaldo Fotográfico (Cámara)" else "Foto de Respaldo Capturada ✓")
            }

            state.respaldoUri?.let { uriStr ->
                AsyncImage(
                    model = Uri.parse(uriStr),
                    contentDescription = "Respaldo fotográfico",
                    modifier = Modifier.fillMaxWidth().height(180.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.guardarVenta() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = !state.guardando
            ) {
                if (state.guardando) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text(if (ventaId == 0) "Guardar Nueva Venta" else "Actualizar Venta")
=======
                onValueChange = viewModel::onObservacionesChange,
                label = { Text("Observaciones") },
                modifier = Modifier.fillMaxWidth()
            )

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
                    Text("Guardar Registro")
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
                }
            }
        }
    }
<<<<<<< HEAD
}

private fun crearUriTemporal(context: Context): Uri {
    val directory = File(context.cacheDir, "images")
    directory.mkdirs()
    val file = File(directory, "respaldo_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
=======
>>>>>>> 1ece1f0 (feat: estructura base, Room, MVVM, VentaViewModel y VentaFormScreen)
}
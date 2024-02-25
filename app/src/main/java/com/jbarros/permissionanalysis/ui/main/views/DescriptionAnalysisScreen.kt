package com.jbarros.permissionanalysis.ui.main.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.jbarros.permissionanalysis.domain.model.ApplicationPermission
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationState

@Composable
fun DescriptionAnalysisScreen(
    onNavigate: (MainDestinations) -> Unit,
    applicationState: ApplicationState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Permission Analysis") },
                navigationIcon = {
                    IconButton(onClick = { onNavigate(MainDestinations.HomeScreen) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()), // Añadir el desplazamiento vertical
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    bitmap = applicationState.selectedApplication.appIcon.toBitmap()
                        .asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 10.dp)
                )
                Column(
                    modifier = Modifier.weight(1f) // Esto permite que el texto ocupe todo el espacio disponible
                ) {
                    Text(
                        text = applicationState.selectedApplication.appName,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = applicationState.selectedApplication.packageName,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            val context = LocalContext.current
            CustomAlertDialogGoToAppStore(applicationState.selectedApplication.packageName)
            Text(
                text = "Categorías de permisos sensibles\n" +
                        " concedidos "
            )
            val descriptions = applicationState.applicationDescriptions
            // Llamar a la función DescriptionsRow para cada propiedad de Descriptions
            DescriptionsRow("Almacenamiento", descriptions.STORAGE)
            DescriptionsRow("Contactos", descriptions.CONTACTS)
            DescriptionsRow("Ubicación", descriptions.LOCATION)
            DescriptionsRow("Cámara", descriptions.CAMERA)
            DescriptionsRow("Microfono", descriptions.MICROPHONE)
            DescriptionsRow("SMS", descriptions.SMS)
            DescriptionsRow("Registro de llamadas", descriptions.CALL_LOG)
            DescriptionsRow("Teléfono", descriptions.PHONE)
            DescriptionsRow("Calendario", descriptions.CALENDAR)
            DescriptionsRow("Configuraciones", descriptions.SETTINGS)
            DescriptionsRow("Tareas", descriptions.TASKS)

        }
    }
}

@Composable
fun DescriptionsRow(descriptionName: String, isChecked: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Etiqueta de la descripción
        Text(
            text = descriptionName,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            style = MaterialTheme.typography.body1
        )

        // Icono de verificación o desmarcado
        Icon(
            imageVector = if (isChecked) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun CustomAlertDialogGoToAppStore(
    packageName: String
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // Handle dismiss action
                showDialog = false
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Text(text = "Saldrás de la aplicación")
                }
            },
            text = {
                Text(text = "Saldrás de la aplicación e irás a la tienda de aplicaciones para que puedas revisar su descripción.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        // Utilizar un Intent para abrir el enlace en el navegador
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
                        ContextCompat.startActivity(context, intent, null)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Ir")
                }
                            },
            dismissButton = {
                Button(
                    onClick = {
                        // Handle dismiss action
                        showDialog = false
                    }
                ) {
                    Text("Quedarme")
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }

    // The button to trigger the dialog
    Button(modifier = Modifier.padding(2.dp).fillMaxWidth()
        ,
        onClick = {
            showDialog = true
        }
    ) {
        Text("Ir a tienda de apps")
    }
}
package com.jbarros.permissionanalysis.ui.main.views

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.jbarros.permissionanalysis.R
import com.jbarros.permissionanalysis.ui.components.ApplicationDetailComponent

import com.jbarros.permissionanalysis.ui.main.LoadingScreen
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationState
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun ApplicationDetailScreen(
    onNavigate: (MainDestinations) -> Unit,
    applicationState: ApplicationState,
    onSelectNewRiskAnalysis: () -> Unit,
    onSelectPermissionChange: () -> Unit,
    onSelectPermissionView: () -> Unit,
    onSelectDownloadReport: () -> Unit
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
        if (!applicationState.loadingApplicationDetailScreen) {
            LoadingScreen()
        } else {
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
                val permissions = applicationState.permissionsNameByApp
                Spacer(modifier = Modifier.height(16.dp))
                ApplicationDetailComponent(
                    applicationState.selectedApplication,
                    applicationState.selectedPermissionAnalysis,
                    permissions
                )
                // Botón básico
                Button(
                    onClick = { onNavigate(MainDestinations.AppliedTechnique) },
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 2.dp)
                        .fillMaxWidth()
                ) {
                    Text("Ver técnicas aplicadas")
                }

                val context = LocalContext.current

                CustomAlertDialogUpdatePermission(context = context, packageName = applicationState.selectedApplication.packageName)

                // Botón básico
                Button(
                    onClick = { onNavigate(MainDestinations.PermissionsChange);onSelectPermissionChange() },
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                ) {
                    Text("Ver cambios de permisos")
                }
                // Botón básico
                Button(
                    onClick = { onNavigate(MainDestinations.PermissionDetail);onSelectPermissionView() },
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                ) {
                    Text("Ver Permisos")
                }

                // Botón básico
                Button(
                    onClick = { onSelectDownloadReport() },
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                ) {
                    Text("Descargar informe aplicacion")
                }
                // Botón básico
                Button(
                    onClick = { onSelectNewRiskAnalysis() },
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                ) {
                    Text("Actualizar permisos")
                }


            }
        }

    }

}


@Composable
fun CustomAlertDialogUpdatePermission(
    context: Context,
    packageName: String
) {
    var showDialog by remember { mutableStateOf(false) }

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
                Text(text = "Saldrás de la aplicación e irás a configuración a actualizar los permisos")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Handle confirm action
                        showDialog = false; openAppSettings(
                        packageName,
                        context
                    )
                    }
                ) {
                    Text("Ir")
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
        Text("Ir a editar permisos")
    }
}

private fun openAppSettings(packageName: String, context: Context) {

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data =
        Uri.parse("package:" + packageName) // Reemplaza "BuildConfig.APPLICATION_ID" con el ID de tu aplicación
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
    context.startActivity(intent)
}
package com.jbarros.permissionanalysis.ui.main.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.ui.components.ApplicationDetailComponent
import com.jbarros.permissionanalysis.ui.components.ApplicationPermissionList
import com.jbarros.permissionanalysis.ui.components.PermissionList
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationState

@Composable
fun AppliedTechniqueScreen(
    onNavigate: (MainDestinations) -> Unit,
    applicationState: ApplicationState,
    onRiskAnalysisSelect: () -> Unit,
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
                .fillMaxWidth(),
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

            // Botón básico
            Button(
                onClick = {onNavigate(MainDestinations.RiskAnalysis);onRiskAnalysisSelect()},
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text("Ver analisis de riesgo")
            }

            // Botón básico
            Button(
                onClick = {onNavigate(MainDestinations.DescriptionAnalysis)},
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text("Ver analisis descripcion")
            }

            // Botón básico
            Button(
                onClick = {onNavigate(MainDestinations.PrivacyPoliciesSummary)},
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text("Ver resumen politica de privacidad")
            }

        }
    }

}

private fun openAppSettings(packageName: String, context: Context) {

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:" + packageName) // Reemplaza "BuildConfig.APPLICATION_ID" con el ID de tu aplicación
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
    context.startActivity(intent)
}
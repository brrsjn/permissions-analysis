package com.jbarros.permissionanalysis.ui.main.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.jbarros.permissionanalysis.domain.model.PermissionsName
import com.jbarros.permissionanalysis.domain.permission.GetPermissionsNameById
import com.jbarros.permissionanalysis.ui.components.ApplicationPermissionList
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationState
import com.jbarros.permissionanalysis.ui.main.interaction.PermissionState

@Composable
fun PermissionDetailScreen(
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
            Text(
                text = "Listado de permisos "
            )
            ApplicationPermissionList(
                modifier = Modifier.padding(16.dp),
                applicationState.permissionsNameByApp
            )
        }
    }

}
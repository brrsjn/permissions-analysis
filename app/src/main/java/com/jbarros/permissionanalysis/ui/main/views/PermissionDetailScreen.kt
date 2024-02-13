package com.jbarros.permissionanalysis.ui.main.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.main.interaction.PermissionState

class PermissionDetailScreen {
}

@Composable
fun PermissionDetailScreen(onNavigate: (MainDestinations) -> Unit,
                           permissionState: PermissionState,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = { onNavigate(MainDestinations.HomeScreen) }) {
            Text(text = "Volver")
        }
        Text(text = "ID: ${permissionState.selectedPermission.id}")
        Text(text = "Name: ${permissionState.selectedPermission.name}")
        Text(text = "Constant Name: ${permissionState.selectedPermission.constantName}")
        Text(text = "Description: ${permissionState.selectedPermission.description}")
        Text(text = "ProtectionLevel: ${permissionState.selectedPermission.protectionLevel}")


    }
}
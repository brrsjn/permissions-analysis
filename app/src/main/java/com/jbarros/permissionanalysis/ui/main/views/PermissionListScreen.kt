package com.jbarros.permissionanalysis.ui.main.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jbarros.permissionanalysis.domain.model.Permission
import com.jbarros.permissionanalysis.ui.components.PermissionList
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.main.interaction.PermissionState


@Composable
fun PermissionListScreen(
    onNavigate: (MainDestinations) -> Unit,
    permissionState: PermissionState,
    onSelectedPermission: (Permission) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background
    ) {

        Column(modifier = Modifier.padding(it)) {
            Button(
                modifier = Modifier.padding(it),
                onClick = { onNavigate(MainDestinations.HomeScreen) }
            ) {
                Text("Volver")
            }
            PermissionList(
                modifier = Modifier.padding(it),
                permissions = permissionState.permissions,
                onSelectedPermission = onSelectedPermission,
                onNavigate = onNavigate
            )
        }
    }
}

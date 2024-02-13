package com.jbarros.permissionanalysis.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jbarros.permissionanalysis.domain.model.Permission
import com.jbarros.permissionanalysis.ui.main.MainDestinations


@Composable
fun PermissionList(
    modifier: Modifier = Modifier,
    permissions: List<Permission>,
    onSelectedPermission: (permission: Permission) -> Unit,
    onNavigate: (MainDestinations) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 175.dp), modifier = modifier.fillMaxSize()
    ) {

        items(items = permissions) { permission ->
            PermissionCard(permission = permission, onNavigate = onNavigate, onSelectPermission = onSelectedPermission)
        }
    }
}
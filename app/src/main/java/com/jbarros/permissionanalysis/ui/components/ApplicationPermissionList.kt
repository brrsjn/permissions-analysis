package com.jbarros.permissionanalysis.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jbarros.permissionanalysis.domain.model.ApplicationPermission

@Composable
fun ApplicationPermissionList(
    modifier: Modifier = Modifier,
    applicationPermissions: List<ApplicationPermission>,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 175.dp), modifier = modifier.fillMaxSize()
    ) {

        items(items = applicationPermissions) { app ->
                ApplicationPermissionItem(permission = app)
        }


    }
}
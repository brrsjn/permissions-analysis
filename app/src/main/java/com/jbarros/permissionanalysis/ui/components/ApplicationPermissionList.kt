package com.jbarros.permissionanalysis.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jbarros.permissionanalysis.domain.model.ApplicationPermission
import com.jbarros.permissionanalysis.domain.model.Permission
import com.jbarros.permissionanalysis.domain.model.PermissionsName

@Composable
fun ApplicationPermissionList(
    modifier: Modifier = Modifier,
    permissionsName: List<PermissionsName>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 175.dp), modifier = modifier.fillMaxSize()
    ) {

        items(items = permissionsName) { appPer ->
                ApplicationPermissionItem(appPer)
        }
    }
}
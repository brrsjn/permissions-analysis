package com.jbarros.permissionanalysis.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jbarros.permissionanalysis.domain.model.ApplicationPermission
import com.jbarros.permissionanalysis.domain.model.Permission
import com.jbarros.permissionanalysis.domain.model.PermissionsName


@Composable
fun ApplicationPermissionItem(permission: PermissionsName) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "${permission.name}", style = MaterialTheme.typography.subtitle1)
            Text(text = "${permission.constantName}", style = MaterialTheme.typography.body2)
            // Optionally, you can format and show the dates
            permission.createdAt?.let {
                Text(text = "Created At: $it", style = MaterialTheme.typography.caption)
            }
            if (permission.updatedAt != "") {
                Text(text = "Updated At: ${permission.updatedAt}", style = MaterialTheme.typography.caption)

            }
        }
        if (permission.isGranted) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Permission Granted",
                tint = Color(0xFF4CAF50) // This is Material Green 500
            )
        } else {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Permission Not Granted",
                tint = Color(0xFFF44336) // This is Material Red 500
            )
        }
    }
}
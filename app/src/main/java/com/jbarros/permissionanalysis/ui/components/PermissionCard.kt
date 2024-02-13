package com.jbarros.permissionanalysis.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jbarros.permissionanalysis.domain.model.Permission
import com.jbarros.permissionanalysis.ui.main.MainDestinations

@Composable
fun PermissionCard(
    modifier: Modifier = Modifier,
    permission: Permission,
    onNavigate: (MainDestinations) -> Unit,
    onSelectPermission: (permission: Permission) -> Unit
) {
    MainDestinations.ApplicationDetail.route = "permissionDetail/"+permission.id
    Card(
        modifier = modifier.clickable { onSelectPermission(permission);onNavigate(MainDestinations.PermissionDetail) },
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(1.dp) // Esto permite que el texto ocupe todo el espacio disponible
        ) {
            Text(
                text = permission.constantName,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = permission.description, maxLines = 3, overflow = TextOverflow.Ellipsis)
            Text(text = permission.protectionLevel)
        }

    }
}
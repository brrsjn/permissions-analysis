package com.jbarros.permissionanalysis.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.domain.model.PermissionAnalysis
import com.jbarros.permissionanalysis.domain.model.PermissionsName

@Composable
fun ApplicationDetailComponent(
    application: Application,
    permissionAnalysis: PermissionAnalysis,
    permissionsName: List<PermissionsName>

) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "ID: ${application.id}")
            Text(text = "Package Name: ${application.packageName}")
            Text(text = "App Name: ${application.appName}")
            Text(text = "Created At: ${application.createdAt}")
            Text(text = "Total permisos ${permissionsName.size}")
            Text(text = "Nivel de riesgo otorgado: ${permissionAnalysis.riskScore}")
            Text(text = "Nivel de riesgo aplicacion: ${permissionAnalysis.riskScoreRequested}")
            Text(text = "Fecha Ultimo analisis de permisos: ${permissionAnalysis.createdAt}")
        }
    }


}
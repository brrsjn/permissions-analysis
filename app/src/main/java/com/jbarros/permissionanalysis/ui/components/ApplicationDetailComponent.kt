package com.jbarros.permissionanalysis.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
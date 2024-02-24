package com.jbarros.permissionanalysis.domain.model

data class ExportData(
    val application: Application,
    val permissionChangeStrings: List<PermissionChangeStrings>,
    val permissionsName: List<PermissionsName>,
    val riskAnalysis: PermissionAnalysis,
    val riskAnalysisInfo: List<PermissionInfo>
)
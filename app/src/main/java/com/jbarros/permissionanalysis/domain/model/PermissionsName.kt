package com.jbarros.permissionanalysis.domain.model

data class PermissionsName(
    val name: String = "",
    val constantName: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val isGranted: Boolean = false,
)
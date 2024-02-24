package com.jbarros.permissionanalysis.domain.model

class ApplicationPermissionsName {
    data class ApplicationPermission(
        val id: Int = 0,
        val applicationUid: Int = 0,
        val permissionId: Int = 0,
        var isGranted: Boolean = false,
        val createdAt: String? = "",
        val updatedAt: String? = ""
    )
}
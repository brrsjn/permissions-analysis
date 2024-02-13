package com.jbarros.permissionanalysis.domain.model

import com.jbarros.permissionanalysis.data.model.ApplicationPermissionEntity


data class ApplicationPermission(
    val id: Int = 0,
    val applicationUid: Int = 0,
    val permissionId: Int = 0,
    var isGranted: Boolean = false,
    val createdAt: String? = "",
    val updatedAt: String? = ""
)


fun ApplicationPermission.toApplicationPermissionEntity(): ApplicationPermissionEntity =
    ApplicationPermissionEntity(
        uid = id,
        applicationUid = applicationUid,
        permissionId = permissionId,
        isGranted = isGranted,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun ApplicationPermissionEntity.toApplicationPermission(): ApplicationPermission =
    ApplicationPermission(
        id = uid,
        applicationUid = applicationUid,
        permissionId = permissionId,
        isGranted = isGranted,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
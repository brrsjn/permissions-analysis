package com.jbarros.permissionanalysis.domain.model

import com.jbarros.permissionanalysis.data.model.PermissionChangeEntity


data class PermissionChange(
    val uid: Int = 0,
    val newState: Boolean,
    val createdAt: String = "",
    var permissionAnalysisUid: Int = 0,
    val applicationPermissionUid: Int = 0
)

fun PermissionChange.toPermissionChangeEntity(): PermissionChangeEntity =
    PermissionChangeEntity(
        uid = uid,
        newState = newState,
        createdAt = createdAt,
        permissionAnalysisUid = permissionAnalysisUid,
        applicationPermissionUid = applicationPermissionUid
    )

fun PermissionChangeEntity.toPermissionChange(): PermissionChange =
    PermissionChange(
        uid = uid,
        newState = newState,
        createdAt = createdAt,
        permissionAnalysisUid = permissionAnalysisUid,
        applicationPermissionUid = applicationPermissionUid
    )
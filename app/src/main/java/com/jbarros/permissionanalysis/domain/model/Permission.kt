package com.jbarros.permissionanalysis.domain.model

import com.jbarros.permissionanalysis.data.model.PermissionEntity

data class Permission(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val protectionLevel: String = "",
    val constantName: String = "",
    val sensitiveDataCategoryId: Int = 0,
    val dangerousGroupId: Int = 0,
)


fun Permission.toPermissionEntity(): PermissionEntity = PermissionEntity(
    uid = id,
    name = name,
    description = description,
    protectionLevel = protectionLevel,
    constantName = constantName,
    sensitiveDataCategoryUid = sensitiveDataCategoryId,
    dangerousGroupUid = dangerousGroupId
)

fun PermissionEntity.toPermission(): Permission = Permission(
    id = uid,
    name = name,
    description = description,
    protectionLevel = protectionLevel,
    constantName = constantName,
    sensitiveDataCategoryId = sensitiveDataCategoryUid,
    dangerousGroupId = dangerousGroupUid
)
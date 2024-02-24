package com.jbarros.permissionanalysis.domain.permission

import com.jbarros.permissionanalysis.data.ApplicationPermissionRepository
import com.jbarros.permissionanalysis.data.PermissionRepository
import com.jbarros.permissionanalysis.domain.model.ApplicationPermission
import com.jbarros.permissionanalysis.domain.model.Permission
import com.jbarros.permissionanalysis.domain.model.PermissionsName
import com.jbarros.permissionanalysis.domain.model.toPermission
import javax.inject.Inject

class GetPermissionsNameById @Inject constructor(
    private val permissionRepository: PermissionRepository,
    private val applicationPermissionRepository: ApplicationPermissionRepository
) {
    suspend operator fun invoke(applicationId: Int): List<PermissionsName> {
        val applicationPermission = applicationPermissionRepository.getAllPermissionByApplicationId(applicationId)
        val permissionListElement = applicationPermission.map {it.permissionId}
        val permissionList = permissionRepository.getByIdList(permissionListElement).map {
            it.toPermission()
        }
        val permissionNameList = mutableListOf<PermissionsName>()
        for (element in permissionList) {
            val applicationPermissionElement =
                applicationPermission.firstOrNull { it.permissionId == element.id }
            var createdAt = applicationPermissionElement?.createdAt
            if (createdAt == null) {
                createdAt = "no-date"
            }
            var updatedAt = applicationPermissionElement?.updatedAt
            if (updatedAt == null) {
                updatedAt = "no-date"
            }
            var isGranted = applicationPermissionElement?.isGranted
            if (isGranted == null) {
                isGranted = false
            }
            val permissionName = PermissionsName(
                name = element.name,
                constantName = element.constantName,
                createdAt = createdAt,
                updatedAt = updatedAt,
                isGranted = isGranted
            )
            permissionNameList.add(permissionName)
        }
        return permissionNameList
    }

}
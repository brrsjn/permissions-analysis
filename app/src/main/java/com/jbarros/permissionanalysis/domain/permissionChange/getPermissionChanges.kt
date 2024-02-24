package com.jbarros.permissionanalysis.domain.permissionChange

import android.util.Log
import com.jbarros.permissionanalysis.data.ApplicationPermissionRepository
import com.jbarros.permissionanalysis.data.PermissionChangeRepository
import com.jbarros.permissionanalysis.data.PermissionRepository
import com.jbarros.permissionanalysis.domain.model.PermissionChange
import com.jbarros.permissionanalysis.domain.model.PermissionChangeStrings
import com.jbarros.permissionanalysis.domain.model.toPermission
import com.jbarros.permissionanalysis.domain.model.toPermissionChange
import javax.inject.Inject

class GetPermissionChanges @Inject constructor(
    private val permissionChangeRepository: PermissionChangeRepository,
    private val applicationPermissionRepository: ApplicationPermissionRepository,
    private val permissionRepository: PermissionRepository
) {
    suspend operator fun invoke(application_uid: Int): List<PermissionChangeStrings> {
        var permissionChange = permissionChangeRepository.getAllByAppId(application_uid)
            .map { it.toPermissionChange() }
        val applicationPermission = applicationPermissionRepository.getAllPermissionByApplicationId(application_uid)
        val permissionListElement = applicationPermission.map {it.permissionId}
        val permissionList = permissionRepository.getByIdList(permissionListElement).map {
            it.toPermission()
        }
        var permissionChangeStrings = mutableListOf<PermissionChangeStrings>()
        for (pc in permissionChange) {
            val permissionId =
                applicationPermission.firstOrNull { it.uid == pc.applicationPermissionUid }?.permissionId
            Log.d("PERMISSION", " permisos id $permissionId")
            var permissionName =
                permissionList.firstOrNull { it.id == permissionId }?.name
            if (permissionName == null) {
                permissionName =
                    permissionList.firstOrNull { it.id == permissionId }?.constantName
            }
            if (permissionName == null) {
                permissionName = "noName"
            }
            val permissionChangeString = PermissionChangeStrings(
                name = permissionName,
                newState = pc.newState,
                createdAt = pc.createdAt
            )
            permissionChangeStrings.add(permissionChangeString)
        }
        return permissionChangeStrings
    }
}
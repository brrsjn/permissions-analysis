package com.jbarros.permissionanalysis.domain.permission

import com.jbarros.permissionanalysis.data.PermissionRepository
import com.jbarros.permissionanalysis.domain.model.Permission
import com.jbarros.permissionanalysis.domain.model.toPermission
import javax.inject.Inject

class GetDangerous @Inject constructor(private val permissionRepository: PermissionRepository) {
    suspend operator fun invoke(): List<Permission> {
        return permissionRepository.getDangerous().map {
            it.toPermission()
        }
    }
}
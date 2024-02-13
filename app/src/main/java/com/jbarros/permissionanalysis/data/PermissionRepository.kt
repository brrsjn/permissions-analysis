package com.jbarros.permissionanalysis.data

import com.jbarros.permissionanalysis.data.local.PermissionDao
import com.jbarros.permissionanalysis.data.model.PermissionEntity
import javax.inject.Inject

class PermissionRepository @Inject constructor(
    private val permissionDao: PermissionDao,
){
    fun getAllPermission(): List<PermissionEntity> {
        return permissionDao.getAll()
    }

    fun getIdByName(name: String): Int {
        return permissionDao.getIdByName(name)
    }

    fun getAllByApplicationId(applicationId: Int): List<PermissionEntity> {
        return permissionDao.getAllByApplicationId(applicationId = applicationId)
    }

    fun insertPermission(permissionEntity: PermissionEntity) {
        permissionDao.insert(permission = permissionEntity)
    }
    fun getDangerous(): List<PermissionEntity>{
        return permissionDao.getDangerous()
    }
}
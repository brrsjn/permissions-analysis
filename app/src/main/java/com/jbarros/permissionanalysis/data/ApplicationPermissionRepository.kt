package com.jbarros.permissionanalysis.data

import com.jbarros.permissionanalysis.data.local.ApplicationPermissionDao
import com.jbarros.permissionanalysis.data.model.ApplicationPermissionEntity
import javax.inject.Inject


class ApplicationPermissionRepository @Inject constructor(
    private val applicationPermissionDao: ApplicationPermissionDao
) {
    fun getAllPermissionByApplicationId(applicationId: Int): List<ApplicationPermissionEntity> {
        return applicationPermissionDao.getAllPermissionByApplicationId(applicationId)
    }

    fun insert(applicationPermissionEntity: ApplicationPermissionEntity) {
        applicationPermissionDao.insert(applicationPermission = applicationPermissionEntity)
    }

    fun update(applicationPermissionEntity: ApplicationPermissionEntity) {
        applicationPermissionDao.update(applicationPermission = applicationPermissionEntity)
    }
}
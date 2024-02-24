package com.jbarros.permissionanalysis.data

import com.jbarros.permissionanalysis.data.local.PermissionChangeDao
import com.jbarros.permissionanalysis.data.model.PermissionChangeEntity
import javax.inject.Inject

class PermissionChangeRepository @Inject constructor(
    private val permissionChangeDao: PermissionChangeDao,
) {
    fun insert(permissionChange: PermissionChangeEntity): Long {
        return permissionChangeDao.insert(permissionChange = permissionChange)
    }

    fun getAllByAppId(applicationUid: Int): List<PermissionChangeEntity> {
        return permissionChangeDao.getAllByAppId(applicationUid)
    }
}
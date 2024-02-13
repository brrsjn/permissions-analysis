package com.jbarros.permissionanalysis.data

import com.jbarros.permissionanalysis.data.local.DangerousGroupDao
import com.jbarros.permissionanalysis.data.model.DangerousGroupEntity

import javax.inject.Inject

class DangerousGroupRepository @Inject constructor(
    private val dangerousGroupDao: DangerousGroupDao
){
    fun getAll(): List<DangerousGroupEntity> {
        return dangerousGroupDao.getAll()
    }

    fun getOne(name: String): DangerousGroupEntity {
        return dangerousGroupDao.getOne(name = name)
    }

    fun insert(name: String) {
        val dangerousGroupEntity = DangerousGroupEntity(name=name)
        dangerousGroupDao.insert(dangerousGroupEntity)
    }

}
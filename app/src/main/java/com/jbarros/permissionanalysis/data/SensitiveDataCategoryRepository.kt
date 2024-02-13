package com.jbarros.permissionanalysis.data

import com.jbarros.permissionanalysis.data.local.SensitiveDataCategoryDao
import com.jbarros.permissionanalysis.data.model.DangerousGroupEntity
import com.jbarros.permissionanalysis.data.model.SensitiveDataCategoryEntity
import javax.inject.Inject

class SensitiveDataCategoryRepository @Inject constructor(
    private val sensitiveDataCategoryDao: SensitiveDataCategoryDao
) {
    fun getAll(): List<SensitiveDataCategoryEntity> {
        return sensitiveDataCategoryDao.getAll()
    }

    fun insert(name: String) {
        val sensitiveDataCategory = SensitiveDataCategoryEntity(name=name)
        sensitiveDataCategoryDao.insert(sensitiveDataCategory)
    }
    fun getOne(name: String): SensitiveDataCategoryEntity {
        return sensitiveDataCategoryDao.getOne(name = name)
    }
}

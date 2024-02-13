package com.jbarros.permissionanalysis.data.local

import androidx.room.*
import com.jbarros.permissionanalysis.data.model.DangerousGroupEntity
import com.jbarros.permissionanalysis.data.model.SensitiveDataCategoryEntity


@Dao
interface SensitiveDataCategoryDao {
    @Query("SELECT * FROM sensitive_data_category")
    fun getAll(): List<SensitiveDataCategoryEntity>

    @Insert
    fun insert(sensitiveDataCategory: SensitiveDataCategoryEntity)

    @Query("SELECT * FROM sensitive_data_category WHERE name = :name LIMIT 1")
    fun getOne(name: String): SensitiveDataCategoryEntity
}
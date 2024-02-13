package com.jbarros.permissionanalysis.data.local

import androidx.room.*
import com.jbarros.permissionanalysis.data.model.ApplicationPermissionEntity
import com.jbarros.permissionanalysis.data.model.DangerousGroupEntity

@Dao
interface DangerousGroupDao {
    @Query("SELECT * FROM dangerous_group")
    fun getAll(): List<DangerousGroupEntity>

    @Query("SELECT * FROM dangerous_group WHERE name = :name LIMIT 1")
    fun getOne(name: String): DangerousGroupEntity

    @Insert
    fun insert(dangerousGroupEntity: DangerousGroupEntity)
}
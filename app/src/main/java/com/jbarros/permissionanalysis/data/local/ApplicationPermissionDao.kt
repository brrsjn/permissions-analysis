package com.jbarros.permissionanalysis.data.local

import androidx.room.*
import com.jbarros.permissionanalysis.data.model.ApplicationPermissionEntity

@Dao
interface ApplicationPermissionDao {
    @Query("SELECT * FROM application_permission")
    fun getAll(): List<ApplicationPermissionEntity>

    @Query("SELECT * FROM application_permission WHERE application_uid = :applicationId")
    fun getAllPermissionByApplicationId(applicationId: Int): List<ApplicationPermissionEntity>

    @Insert
    fun insert(applicationPermission: ApplicationPermissionEntity)

    @Delete
    fun delete(applicationPermission: ApplicationPermissionEntity)

    @Update
    fun update(applicationPermission: ApplicationPermissionEntity)
}
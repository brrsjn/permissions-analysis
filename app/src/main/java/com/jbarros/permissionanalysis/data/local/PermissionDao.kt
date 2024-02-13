package com.jbarros.permissionanalysis.data.local

import androidx.room.*
import com.jbarros.permissionanalysis.data.model.PermissionEntity

@Dao
interface PermissionDao {
    @Query("SELECT * FROM permission")
    fun getAll(): List<PermissionEntity>

    @Query("SELECT * FROM permission WHERE protection_level = 'dangerous'")
    fun getDangerous(): List<PermissionEntity>

    @Query("SELECT uid FROM permission WHERE constant_name = :name")
    fun getIdByName(name: String): Int


    @Query("SELECT * FROM permission p LEFT JOIN application_permission ap ON ap.permission_uid = p.uid WHERE ap.application_uid = :applicationId")
    fun getAllByApplicationId(applicationId: Int): List<PermissionEntity>

    @Insert
    fun insert(permission: PermissionEntity)

    @Delete
    fun delete(permission: PermissionEntity)

    @Update
    fun update(permission: PermissionEntity)
}
package com.jbarros.permissionanalysis.data.local

import androidx.room.*
import com.jbarros.permissionanalysis.data.model.ApplicationPermissionEntity
import com.jbarros.permissionanalysis.domain.model.PermissionInfo

@Dao
interface ApplicationPermissionDao {
    @Query("SELECT * FROM application_permission")
    fun getAll(): List<ApplicationPermissionEntity>

    @Query("SELECT * FROM application_permission WHERE application_uid = :applicationId")
    fun getAllPermissionByApplicationId(applicationId: Int): List<ApplicationPermissionEntity>

    @Query("SELECT sdc.name AS sensitiveDataCategoryName, p.name AS permissionName " +
            "FROM application_permission ap " +
            "INNER JOIN permission p ON ap.permission_uid = p.uid " +
            "INNER JOIN sensitive_data_category sdc ON p.sensitive_data_category_uid = sdc.uid " +
            "WHERE application_uid = :applicationId")
    fun getSensitiveDataCategoryAndPermissionName(applicationId: Int): List<PermissionInfo>

    @Insert
    fun insert(applicationPermission: ApplicationPermissionEntity)

    @Delete
    fun delete(applicationPermission: ApplicationPermissionEntity)

    @Update
    fun update(applicationPermission: ApplicationPermissionEntity)
}
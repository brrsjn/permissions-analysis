package com.jbarros.permissionanalysis.data.local

import androidx.room.*
import com.jbarros.permissionanalysis.data.model.ApplicationEntity

@Dao
interface ApplicationDao {
    @Query("SELECT * FROM application WHERE deleted_at IS NULL")
    fun getAll(): List<ApplicationEntity>

    @Query("SELECT * FROM application WHERE uid = :applicationId LIMIT 1")
    fun getOne(applicationId: Int): ApplicationEntity

    @Insert
    fun insert(application: ApplicationEntity)

    @Delete
    fun delete(application: ApplicationEntity)

    @Update
    fun update(application: ApplicationEntity)

    @Query("SELECT * FROM application WHERE application.package_name = :packageName LIMIT 1")
    fun getApplicationIdByPackageName(packageName: String): ApplicationEntity
}
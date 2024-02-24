package com.jbarros.permissionanalysis.data.local

import androidx.room.*
import com.jbarros.permissionanalysis.data.model.PermissionAnalysisEntity

@Dao
interface PermissionAnalysisDao {
    @Query("SELECT COUNT(permission_analysis.uid) FROM permission_analysis")
    fun count(): Int

    @Query("SELECT * FROM permission_analysis")
    fun getAll(): List<PermissionAnalysisEntity>

    @Query("SELECT * FROM permission_analysis WHERE permission_analysis.application_uid = :application_uid")
    fun getAllByApplicationId(application_uid: Int): List<PermissionAnalysisEntity>

    @Query("SELECT * FROM permission_analysis WHERE permission_analysis.application_uid = :application_uid ORDER BY permission_analysis.created_at DESC LIMIT 1")
    fun getByApplicationId(application_uid: Int): PermissionAnalysisEntity

    @Insert
    fun insert(permissionAnalysis: PermissionAnalysisEntity): Long

    @Delete
    fun delete(permissionAnalysis: PermissionAnalysisEntity)

    @Update
    fun update(permissionAnalysis: PermissionAnalysisEntity)
}
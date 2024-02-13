package com.jbarros.permissionanalysis.data.applications

import com.jbarros.permissionanalysis.data.local.PermissionAnalysisDao
import com.jbarros.permissionanalysis.data.model.PermissionAnalysisEntity
import com.jbarros.permissionanalysis.utils.PackageManagerSource
import javax.inject.Inject

class PermissionAnalysisRepository @Inject constructor(
    private val permissionAnalysisDao: PermissionAnalysisDao,
    private val packageManagerDataSource: PackageManagerSource
){
    fun count(): Int {
        return permissionAnalysisDao.count()
    }

    fun getAllPermissionAnalysis(): List<PermissionAnalysisEntity> {
        return permissionAnalysisDao.getAll()
    }

    fun getGrantedPermission(packageName: String): MutableList<String> {
        return packageManagerDataSource.getGrantedPermissions(packageName = packageName)
    }

    fun getRequestedPermission(packageName: String): List<String> {
        return packageManagerDataSource.getRequestedPermissions(packageName = packageName)
    }

    fun getPermissionAnalysisByApplicationId(applicationId: Int): PermissionAnalysisEntity {
        return permissionAnalysisDao.getByApplicationId(applicationId)
    }

    fun getAllPermissionAnalysisByApplicationId(applicationId: Int): List<PermissionAnalysisEntity> {
        return permissionAnalysisDao.getAllByApplicationId(applicationId)
    }

    fun insertPermissionAnalysis(permissionAnalysisEntity: PermissionAnalysisEntity){
        permissionAnalysisDao.insert(permissionAnalysis = permissionAnalysisEntity )
    }
}
package com.jbarros.permissionanalysis.domain.exportData

import com.jbarros.permissionanalysis.data.ApplicationPermissionRepository
import com.jbarros.permissionanalysis.data.ApplicationRepository
import com.jbarros.permissionanalysis.data.PermissionRepository
import com.jbarros.permissionanalysis.domain.model.ExportData
import com.jbarros.permissionanalysis.domain.model.PermissionsName
import com.jbarros.permissionanalysis.domain.model.toApplication
import com.jbarros.permissionanalysis.domain.model.toPermission
import com.jbarros.permissionanalysis.domain.permission.GetPermissionsNameById
import com.jbarros.permissionanalysis.domain.permissionChange.GetPermissionChanges
import com.jbarros.permissionanalysis.domain.permissionanalysis.GetPermissionAnalysis
import com.jbarros.permissionanalysis.domain.riskAnalysis.GetSensitiveDataCategoryAndPermission
import javax.inject.Inject

class ExportFileData @Inject constructor(
    private val applicationRepository: ApplicationRepository,
    private val getPermissionChanges: GetPermissionChanges,
    private val getPermissionsNameById: GetPermissionsNameById,
    private val getSensitiveDataCategoryAndPermission: GetSensitiveDataCategoryAndPermission,
    private val getPermissionAnalysis: GetPermissionAnalysis
) {
    suspend operator fun invoke(applicationId: Int): ExportData {
        val application = applicationRepository.getOne(applicationId).toApplication()
        val permissionChanges = getPermissionChanges.invoke(applicationId)
        val permissionsName = getPermissionsNameById.invoke(applicationId)
        val permissionInfo = getSensitiveDataCategoryAndPermission.invoke(applicationId)
        val permissionAnalysis = getPermissionAnalysis.invoke(applicationId)
        return ExportData(application = application, permissionChangeStrings = permissionChanges, permissionsName = permissionsName, riskAnalysis = permissionAnalysis , riskAnalysisInfo = permissionInfo)
    }

}
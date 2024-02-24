package com.jbarros.permissionanalysis.domain.permissionanalysis;

import com.jbarros.permissionanalysis.data.ApplicationPermissionRepository
import com.jbarros.permissionanalysis.data.applications.PermissionAnalysisRepository
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.domain.model.toApplicationPermission
import javax.inject.Inject;

class NewRiskAnalysis @Inject constructor(
    private val applicationPermissionRepository: ApplicationPermissionRepository,
    private val permissionAnalysisRepository: PermissionAnalysisRepository
) {
    suspend operator fun invoke(application: Application) {
        val grantedPermission =
            permissionAnalysisRepository.getGrantedPermission(packageName = application.packageName)
        val requestedPermission =
            permissionAnalysisRepository.getRequestedPermission(packageName = application.packageName)

        val permissionByApplicationId =
            applicationPermissionRepository.getAllPermissionByApplicationId(applicationId = application.id)
                .map { it.toApplicationPermission() }


        return
    }
}

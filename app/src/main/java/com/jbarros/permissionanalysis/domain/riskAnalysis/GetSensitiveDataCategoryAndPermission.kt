package com.jbarros.permissionanalysis.domain.riskAnalysis

import com.jbarros.permissionanalysis.data.ApplicationPermissionRepository

import com.jbarros.permissionanalysis.domain.model.PermissionInfo
import javax.inject.Inject

class GetSensitiveDataCategoryAndPermission @Inject constructor(
    private val applicationPermissionRepository: ApplicationPermissionRepository
) {
    suspend operator fun invoke(applicationId: Int): List<PermissionInfo>{
        return applicationPermissionRepository.getSensitiveDataCategoryAndPermissionName(applicationId = applicationId)
    }
}
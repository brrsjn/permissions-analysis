package com.jbarros.permissionanalysis.domain.applicationpermission

import com.jbarros.permissionanalysis.data.ApplicationPermissionRepository
import com.jbarros.permissionanalysis.domain.model.ApplicationPermission
import com.jbarros.permissionanalysis.domain.model.toApplicationPermission
import javax.inject.Inject

class GetApplicationPermissionByApp @Inject constructor(
    private val applicationPermissionRepository: ApplicationPermissionRepository
) {
    suspend operator fun invoke(applicationId: Int): List<ApplicationPermission>{
        return applicationPermissionRepository.getAllPermissionByApplicationId(applicationId = applicationId).map{it.toApplicationPermission()}
    }
}
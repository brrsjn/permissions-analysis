package com.jbarros.permissionanalysis.domain.permissionanalysis

import com.jbarros.permissionanalysis.data.ApplicationRepository
import com.jbarros.permissionanalysis.data.local.ApplicationDao
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.domain.model.toApplication
import javax.inject.Inject

class NewAppPermissionAnalysisByPackageName @Inject constructor(
    private val newAppPermissionAnalysis: NewAppPermissionAnalysis,
    private val applicationRepository: ApplicationRepository
) {
    suspend operator fun invoke(packageName: String) {
        val application = applicationRepository.getApplicationIdByPackageName(packageName)
        newAppPermissionAnalysis.invoke(application.toApplication())
    }

}
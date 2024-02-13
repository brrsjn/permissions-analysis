package com.jbarros.permissionanalysis.domain.permissionanalysis

import com.jbarros.permissionanalysis.data.ApplicationRepository

import com.jbarros.permissionanalysis.domain.model.toApplication
import javax.inject.Inject

class NewPermissionAnalysis @Inject constructor(
    private val applicationRepository: ApplicationRepository,
    private val newAppPermissionAnalysis: NewAppPermissionAnalysis
) {
    suspend operator fun invoke() {
        for (i in applicationRepository.getAllApplications()) {
            newAppPermissionAnalysis.invoke(i.toApplication())
        }
        return
    }
}
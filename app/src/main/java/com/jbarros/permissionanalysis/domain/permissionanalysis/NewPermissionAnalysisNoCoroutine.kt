package com.jbarros.permissionanalysis.domain.permissionanalysis

import com.jbarros.permissionanalysis.data.ApplicationRepository
import com.jbarros.permissionanalysis.domain.model.toApplication
import javax.inject.Inject

class NewPermissionAnalysisNoCoroutine @Inject constructor(
    private val applicationRepository: ApplicationRepository,
    private val newAppPermissionAnalysisNoCoroutine: NewAppPermissionAnalysisNoCoroutine
) {
    operator fun invoke() {
        for (i in applicationRepository.getAllApplications()) {
            newAppPermissionAnalysisNoCoroutine.invoke(i.toApplication())
        }
        return
    }
}
package com.jbarros.permissionanalysis.domain.permissionanalysis

import com.jbarros.permissionanalysis.data.applications.PermissionAnalysisRepository

import com.jbarros.permissionanalysis.domain.model.PermissionAnalysis
import com.jbarros.permissionanalysis.domain.model.toApplication

import javax.inject.Inject

class GetPermissionAnalysis @Inject constructor(private val permissionAnalysisRepository: PermissionAnalysisRepository) {
    suspend operator fun invoke(application_uid: Int): PermissionAnalysis {
        return permissionAnalysisRepository.getPermissionAnalysisByApplicationId(application_uid).toApplication()
    }
}
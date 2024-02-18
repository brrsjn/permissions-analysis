package com.jbarros.permissionanalysis.data

import com.jbarros.permissionanalysis.network.PermissionsAnalysisAPIService
import com.jbarros.permissionanalysis.network.model.PermissionExtractInfoData
import com.jbarros.permissionanalysis.network.model.PrivacyPoliciesData
import javax.inject.Inject

class PrivacyPoliciesRepository @Inject constructor(
    private val permissionsAnalysisAPIService: PermissionsAnalysisAPIService
) {
    suspend fun getPrivacyPolicies(packageName: String): PrivacyPoliciesData {
        return permissionsAnalysisAPIService.getPrivacyPolicies(packageName).data
    }
}
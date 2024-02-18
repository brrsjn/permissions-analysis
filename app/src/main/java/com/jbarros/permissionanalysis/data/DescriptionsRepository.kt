package com.jbarros.permissionanalysis.data

import com.jbarros.permissionanalysis.network.PermissionsAnalysisAPIService
import com.jbarros.permissionanalysis.network.model.DescriptionsData
import javax.inject.Inject

class DescriptionsRepository @Inject constructor(
    private val permissionsAnalysisAPIService: PermissionsAnalysisAPIService
) {
    suspend fun getDescriptions(packageName: String): DescriptionsData {
        return permissionsAnalysisAPIService.getDescriptions(packageName).data
    }
}
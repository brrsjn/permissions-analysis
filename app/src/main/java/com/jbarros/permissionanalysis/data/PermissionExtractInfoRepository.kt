package com.jbarros.permissionanalysis.data

import com.jbarros.permissionanalysis.network.PermissionsAnalysisAPIService
import com.jbarros.permissionanalysis.network.model.PermissionExtractInfoData
import javax.inject.Inject

class PermissionExtractInfoRepository @Inject constructor(
    private val permissionExtractInfoService: PermissionsAnalysisAPIService
) {
    suspend fun getPermissionList(): List<PermissionExtractInfoData> {
        return permissionExtractInfoService.getListPermission().data
    }
}